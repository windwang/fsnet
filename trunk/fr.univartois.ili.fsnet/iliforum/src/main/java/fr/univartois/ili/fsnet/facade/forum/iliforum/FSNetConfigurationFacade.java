package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Property;

/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 * 
 */

public class FSNetConfigurationFacade {

    private final EntityManager em;

    public FSNetConfigurationFacade(EntityManager em) {
        this.em = em;
    }

    public int getSMTPPort() {
        return Integer.parseInt(em.find(Property.class,
                Property.FsnetProperty.SMTP_PORT).getValue());
    }

    public void setSMTPPort(int smtpPort) {
        Property smtpPortConf = em.find(Property.class,
                Property.FsnetProperty.SMTP_PORT);
        if (smtpPortConf == null) {
            smtpPortConf = new Property();
            smtpPortConf.setKey(Property.FsnetProperty.SMTP_PORT);
            smtpPortConf.setValue(String.valueOf(smtpPort));
            em.persist(smtpPortConf);
        } else {
            smtpPortConf.setValue(String.valueOf(smtpPort));
            em.merge(smtpPortConf);
        }

    }

    public String getSMTPHost() {
        return em.find(Property.class,
                Property.FsnetProperty.SMTP_HOST).getValue();
    }

    public void setSMTPHost(String smtpHost) {
        Property smtpHostConf = em.find(Property.class,
                Property.FsnetProperty.SMTP_HOST);
        if (smtpHostConf == null) {
            smtpHostConf = new Property();
            smtpHostConf.setKey(Property.FsnetProperty.SMTP_HOST);
            smtpHostConf.setValue(smtpHost);
            em.persist(smtpHostConf);
        } else {
            smtpHostConf.setValue(smtpHost);
            em.merge(smtpHostConf);
        }
    }

    public String getSMTPUser() {
        return em.find(Property.class,
                Property.FsnetProperty.SMTP_USER).getValue();
    }

    public void setSMTPUser(String smtpUser) {
        Property smtpUserConf = em.find(Property.class,
                Property.FsnetProperty.SMTP_USER);
        if(smtpUserConf == null) {
            smtpUserConf = new Property();
            smtpUserConf.setKey(Property.FsnetProperty.SMTP_USER);
            smtpUserConf.setValue(smtpUser);
            em.persist(smtpUserConf);
        } else {
            smtpUserConf.setValue(smtpUser);
            em.merge(smtpUserConf);
        }
    }
   
    public String getSMTPPassword() {
        return em.find(Property.class,
                Property.FsnetProperty.SMTP_PASSWORD).getValue();
    }

    public void setSMTPPassword(String smtpPassword) {
        Property smtpPasswordConf = em.find(Property.class,
                Property.FsnetProperty.SMTP_PASSWORD);
        if(smtpPasswordConf == null) {
            smtpPasswordConf = new Property();
            smtpPasswordConf.setKey(Property.FsnetProperty.SMTP_PASSWORD);
            smtpPasswordConf.setValue(smtpPassword);
            em.persist(smtpPasswordConf);
        } else {
            smtpPasswordConf.setValue(smtpPassword);
            em.merge(smtpPasswordConf);
        }
    }
   
    public String getTLS() {
        return em.find(Property.class,
                Property.FsnetProperty.ENABLE_TLS).getValue();
    }

    public void setTLS(Boolean tls) {
        Property smtpTLSConf = em.find(Property.class,
                Property.FsnetProperty.ENABLE_TLS);
        if(smtpTLSConf == null) {
            smtpTLSConf = new Property();
            smtpTLSConf.setKey(Property.FsnetProperty.ENABLE_TLS);
            smtpTLSConf.setValue(String.valueOf(tls));
            em.persist(smtpTLSConf);
        } else {
            smtpTLSConf.setValue(String.valueOf(tls));
            em.merge(smtpTLSConf);
        }
    }
   
    public String getSSL() {
        return em.find(Property.class,
                Property.FsnetProperty.ENABLE_SSL).getValue();
    }

    public void setSSL(Boolean ssl) {
        Property smtpSSLConf = em.find(Property.class,
                Property.FsnetProperty.ENABLE_SSL);
        if(smtpSSLConf == null) {
            smtpSSLConf = new Property();
            smtpSSLConf.setKey(Property.FsnetProperty.ENABLE_SSL);
            smtpSSLConf.setValue(String.valueOf(ssl));
            em.persist(smtpSSLConf);
        } else {
            smtpSSLConf.setValue(String.valueOf(ssl));
            em.merge(smtpSSLConf);
        }
    }
   
   
    public String getAuthentification() {
        return em.find(Property.class,
                Property.FsnetProperty.ENABLE_AUTHENTICATION).getValue();
    }

    public void setAuthentification(Boolean authentification) {
        Property smtpAuthentificationConf = em.find(Property.class,
                Property.FsnetProperty.ENABLE_AUTHENTICATION);
        if(smtpAuthentificationConf == null) {
            smtpAuthentificationConf = new Property();
            smtpAuthentificationConf.setKey(Property.FsnetProperty.ENABLE_AUTHENTICATION);
            smtpAuthentificationConf.setValue(String.valueOf(authentification));
            em.persist(smtpAuthentificationConf);
        } else {
            smtpAuthentificationConf.setValue(String.valueOf(authentification));
            em.merge(smtpAuthentificationConf);
        }
    }
   
    public String getMailFrom() {
        return em.find(Property.class,
                Property.FsnetProperty.MAIL_FROM).getValue();
    }

    public void setMailFrom(String mailFrom) {
        Property smtpMailFromConf = em.find(Property.class,
                Property.FsnetProperty.MAIL_FROM);
        if(smtpMailFromConf == null) {
            smtpMailFromConf = new Property();
            smtpMailFromConf.setKey(Property.FsnetProperty.MAIL_FROM);
            smtpMailFromConf.setValue(mailFrom);
            em.persist(smtpMailFromConf);
        } else {
            smtpMailFromConf.setValue(mailFrom);
            em.merge(smtpMailFromConf);
        }
    }
   
    public String getUrl() {
        return em.find(Property.class,
                Property.FsnetProperty.URL).getValue();
    }

    public void setUrl(String url) {
        Property smtpUrlConf = em.find(Property.class,
                Property.FsnetProperty.URL);
        if(smtpUrlConf == null) {
            smtpUrlConf = new Property();
            smtpUrlConf.setKey(Property.FsnetProperty.URL);
            smtpUrlConf.setValue(url);
            em.persist(smtpUrlConf);
        } else {
            smtpUrlConf.setValue(url);
            em.merge(smtpUrlConf);
        }
    }

}
