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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;

public class ImageManager {

	private static final String MINIATURE_SUFFIX = ".miniature";
	private static final String PICTURE_FORMAT = ".png";
	
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
				PICTURE_MAX_WIDTH_OR_HEIGHT);
		ImageManager.installPicture(Integer.toString(userId), picture);

		BufferedImage miniature = getProperResizedImage(incommingPicture,
				MINIATURE_MAX_WIDTH_OR_HEIGHT);
		ImageManager.installPicture(
				Integer.toString(userId) + MINIATURE_SUFFIX, miniature);
	}

	public static void removeOldUserPicture(int userId) {
		String directory = getStorageDirectory();
		if (directory != null) {
			removeOldPicture(directory + userId + PICTURE_FORMAT);
			removeOldPicture(directory + userId + MINIATURE_SUFFIX + PICTURE_FORMAT);
		}
	}

	public static void sendUserMiniature(int userId,
			HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
			throws IOException {
		String directory = getStorageDirectory();
		String fileName;
		if (directory != null) {
			fileName = directory + Integer.toString(userId) + MINIATURE_SUFFIX
					+ PICTURE_FORMAT;
			File pictureFile = new File(fileName);
			if (pictureFile.exists()) {
				sendPicture(pictureFile, response);	
			} else {
				sendDefaultMiniature(response, servletContext);
			}
		} else {
			sendDefaultMiniature(response, servletContext);
		}
	}

	public static void sendUserPicture(int userId, HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		String directory = getStorageDirectory();
		String fileName;
		if (directory != null) {
			fileName = directory + Integer.toString(userId) + PICTURE_FORMAT;
			File pictureFile = new File(fileName);
			if (pictureFile.exists()) {
				sendPicture(pictureFile, response);	
			} else {
				sendDefaultPicture(response, context);			
			}
		} else {
			sendDefaultPicture(response, context);
		}
	}
	
	private static void sendDefaultMiniature(HttpServletResponse response,
			ServletContext context) throws IOException {
		InputStream stream = context.getResourceAsStream("/images/DefaultMiniature.png");
		sendPicture(stream, response);
	}
	private static void sendDefaultPicture(HttpServletResponse response, ServletContext context) throws IOException {
		InputStream stream = context.getResourceAsStream("/images/DefaultPhoto.png");
		sendPicture(stream, response);
	}
	
	private static final int BUFFER_SIZE = 4096;

	private static void sendPicture(InputStream stream,
			HttpServletResponse response) throws IOException {
		byte[] datas = new byte[BUFFER_SIZE];
		int numRead = stream.read(datas);
		OutputStream out = response.getOutputStream();
		while (numRead != -1) {
			out.write(datas, 0 , numRead);
			numRead = stream.read(datas);
		}
		stream.close();
		out.flush();
	}
	
	private static void sendPicture(File picture,
			HttpServletResponse response) throws IOException {
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(picture));
			sendPicture(stream, response);
		} catch (FileNotFoundException e) {
			
		}
	}

	/*
	 * The maximum width or height for the user picture (ratio is preserved)
	 */
	private static final int PICTURE_MAX_WIDTH_OR_HEIGHT = 100;
	private static final int MINIATURE_MAX_WIDTH_OR_HEIGHT = 40;

	private static BufferedImage getProperResizedImage(
			BufferedImage incommingImage, int maxWidthOrHeight) throws FileNotFoundException, IOException {
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
				(int) (incommingImage.getHeight() * scaleValue), 
            BufferedImage.TYPE_INT_ARGB);
            //pictureType.getImageType());
		op.filter(incommingImage, properResized);
		return properResized;
	}

	private static BufferedImage convert(BufferedImage image) {
		BufferedImage img = new BufferedImage(image.getWidth(), image
				.getHeight(), Transparency.TRANSLUCENT);
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return img;
	}

	/*
	 * install the picture in the proper directory
	 * 
	 * @param fileName The file name
	 * 
	 * @param suffix the file's sufix (.png, .bmp...)
	 * 
	 * @param datas the picture's datas
	 */
	private static void installPicture(String fileName,
			BufferedImage image)
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
		File oldPicture = new File(fileName);
		if (oldPicture != null) {
			oldPicture.delete();
		}
	}
	
	private static void sendLogo(InputStream stream,
			HttpServletResponse response) throws IOException {
		byte[] datas = new byte[BUFFER_SIZE];
		int numRead = stream.read(datas);
		OutputStream out = response.getOutputStream();
		while (numRead != -1) {
			out.write(datas, 0 , numRead);
			numRead = stream.read(datas);
		}
		stream.close();
		out.flush();
	}
	public static void sendLogo(Integer groupId,HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		String directory = getStorageDirectory();
		String fileName;
		if (directory != null) {
			fileName = directory + Integer.toString(groupId) +PICTURE_FORMAT;
			File pictureFile = new File(fileName);
			if (pictureFile.exists()) {
				sendLogo(pictureFile, response);	
			} else {
				sendDefaultLogo(response, context);			
			}
		} else {
			sendDefaultLogo(response, context);
		}
	}
	

	private static void sendDefaultLogo(HttpServletResponse response, ServletContext context) throws IOException {
		InputStream stream = context.getResourceAsStream("/images/FSNET.png");
		sendLogo(stream, response);
	}
	private static void sendLogo(File logo,
			HttpServletResponse response) throws IOException {
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(logo));
			sendLogo(stream, response);
		} catch (FileNotFoundException e) {
			
		}
	}
	public static void createLogo(Integer groupId,
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
		BufferedImage logo = getProperResizedImage(incommingPicture,
				PICTURE_MAX_WIDTH_OR_HEIGHT);
		
		ImageManager.installLogo(Integer.toString(groupId),logo);
		
	}
	private static void installLogo(String fileName,BufferedImage image)
			throws IllegalStateException {
		String directory = getStorageDirectory();
		if (directory != null) {
			removeOldPicture(directory + fileName +PICTURE_FORMAT);
			String fileToCreate = directory +fileName+ PICTURE_FORMAT;
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
	

}
