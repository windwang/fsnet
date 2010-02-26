package fr.univartois.ili.fsnet.actions.utils;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;

public class ImageManager {
	
	private static final String MINIATURE_SUFFIX = ".miniature";

	public static void createPicturesForUser(int userId,
			InputStream incommingPictureInputStream, PictureType pictureType)
			throws FileNotFoundException, IOException, IllegalStateException {
		BufferedImage incommingPicture = ImageIO
				.read(incommingPictureInputStream);
		if (incommingPicture == null) {
			throw new IllegalStateException();
		}
		if (pictureType.equals(PictureType.PNG)) {
			incommingPicture = convert(incommingPicture);
		}
		BufferedImage picture = getProperResizedImage(incommingPicture,
				pictureType, PICTURE_MAX_WIDTH_OR_HEIGHT);
		ImageManager.installPicture(Integer.toString(userId), pictureType,
				picture);

		BufferedImage miniature = getProperResizedImage(incommingPicture,
				pictureType, MINIATURE_MAX_WIDTH_OR_HEIGHT);
		ImageManager.installPicture(Integer.toString(userId) + MINIATURE_SUFFIX,
				pictureType, miniature);
	}

	public static void removeOldUserPicture(int userId) {
		String directory = getStorageDirectory();
		if (directory != null) {
			removeOldPicture(directory + userId);
			removeOldPicture(directory + userId + MINIATURE_SUFFIX);
		}
	}

	public static void sendUserMiniature(int userId,
			HttpServletResponse response) throws IOException {
		sendPicture(userId + MINIATURE_SUFFIX, response,
				"images/DefaultMiniature.png");
	}

	public static void sendUserPicture(int userId, HttpServletResponse response)
			throws IOException {
		sendPicture(Integer.toString(userId), response,
				"images/DefaultPhoto.png");
	}

	private static void sendPicture(String fileName,
			HttpServletResponse response, String defaultPicture)
			throws IOException {
		String directory = getStorageDirectory();
		byte[] datas = null;
		if (directory != null) {
			File picture = searchPicture(directory + fileName);
			if (picture != null) {
				PictureType pictureType = null;
				for (PictureType pt : PictureType.values()) {
					if (picture.getName().endsWith(pt.getSuffix())) {
						pictureType = pt;
					}
				}
				response.setContentType(pictureType.getMimeType());
				response.setContentLength((int) picture.length());
				response.addHeader("Content-Disposition", "attachment; filename="
				          + fileName + pictureType.getSuffix());
				datas = new byte[(int) picture.length()];
				BufferedInputStream stream;
				try {
					stream = new BufferedInputStream(new FileInputStream(
							picture));
					stream.read(datas);
					OutputStream out = response.getOutputStream();
					out.write(datas);
				} catch (FileNotFoundException e) {
					response.sendRedirect(defaultPicture);
				}
			} else {
				response.sendRedirect(defaultPicture);
			}
		} else {
			response.sendRedirect(defaultPicture);
		}
	}

	/*
	 * The maximum width or height for the user picture (ratio is preserved)
	 */
	private static final int PICTURE_MAX_WIDTH_OR_HEIGHT = 100;
	private static final int MINIATURE_MAX_WIDTH_OR_HEIGHT = 25;

	private static BufferedImage getProperResizedImage(
			BufferedImage incommingImage, PictureType pictureType,
			int maxWidthOrHeight) throws FileNotFoundException, IOException {
		AffineTransform tx = new AffineTransform();
		double scaleValue;
		if (incommingImage.getWidth() > incommingImage.getHeight()) {
			scaleValue = maxWidthOrHeight / (double) incommingImage.getWidth();
		} else {
			scaleValue = maxWidthOrHeight / (double) incommingImage.getHeight();
		}

		tx.scale(scaleValue, scaleValue);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BICUBIC);
		BufferedImage properResized = new BufferedImage((int) (incommingImage
				.getWidth() * scaleValue),
				(int) (incommingImage.getHeight() * scaleValue), pictureType
						.getImageType());
		op.filter(incommingImage, properResized);
		return properResized;
	}
	
	public static BufferedImage convert(BufferedImage image) {
        BufferedImage img = new BufferedImage(image.getWidth(),
                image.getHeight(),
                Transparency.TRANSLUCENT);
        Graphics2D g2 = (Graphics2D)img.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return img;
    }

	/*
	 * install the picture in the proper directory
	 * 
	 * @param fileName The file name
	 * @param suffix the file's sufix (.png, .bmp...)
	 * @param datas the picture's datas
	 */
	private static void installPicture(String fileName,
			PictureType pictureType, BufferedImage image)
			throws IllegalStateException {
		String directory = getStorageDirectory();
		if (directory != null) {
			removeOldPicture(directory + fileName);
			String fileToCreate = directory + fileName
					+ PictureType.PNG.getSuffix();
			File imageFile = new File(fileToCreate);
			try {
				OutputStream out = new FileOutputStream(imageFile);
				ImageIO.write(image, "png", out);
				out.flush();
				out.close();
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
				throw new IllegalStateException(e);
			}
		} else {
			Logger.getAnonymousLogger().severe(
					"The storage directory for pictures is not configured");
			throw new IllegalStateException();
		}
	}

	/*
	 * Return the path of the directory that receive pictures
	 */
	private static String getStorageDirectory() {
		String directory = FSNetConfiguration.getInstance()
				.getFSNetConfiguration().getProperty(
						FSNetConfiguration.PICTURES_DIRECTORY_KEY);
		if (directory != null) {
			if (!directory.endsWith("/")) {
				directory = directory + "/";
			}
		}
		return directory;
	}

	/*
	 * Delete the picture currently installed on the file system
	 */
	private static void removeOldPicture(String fileName) {
		File oldPicture = searchPicture(fileName);
		if (oldPicture != null) {
			oldPicture.delete();
		}
	}

	/*
	 * return the java.io.File represented by filename on the file system
	 * 
	 * @param fileName the filename without suffix
	 */
	private static File searchPicture(String fileName) {
		for (PictureType pictureType : PictureType.values()) {
			File f = new File(fileName + pictureType.getSuffix());
			if (f.exists()) {
				return f;
			}
		}
		return null;
	}

}
