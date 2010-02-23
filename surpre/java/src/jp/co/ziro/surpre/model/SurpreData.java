package jp.co.ziro.surpre.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

@Model
public class SurpreData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute
    private User user; 
  
    @Attribute
    private String wishListId;

    @Attribute
    private String mixiId;

    @Attribute
    private String twitterToken;
    @Attribute
    private String twitterTokenSecret;
    @Attribute
    private String flickrToken;
    @Attribute
    private String flickrNsid; 

    private Integer schemaVersion = 1;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Returns the schema version.
     *
     * @return the schema version
     */
    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Sets the schema version.
     *
     * @param schemaVersion
     *            the schema version
     */
    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SurpreData other = (SurpreData) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setMixiId(String mixiId) {
        this.mixiId = mixiId;
    }

    public String getMixiId() {
        return mixiId;
    }

    public void setTwitterToken(String twitterToken) {
        this.twitterToken = twitterToken;
    }

    public String getTwitterToken() {
        return twitterToken;
    }

    public void setTwitterTokenSecret(String twitterTokenSecret) {
        this.twitterTokenSecret = twitterTokenSecret;
    }

    public String getTwitterTokenSecret() {
        return twitterTokenSecret;
    }

    public void setFlickrToken(String flickerToken) {
        this.flickrToken = flickerToken;
    }

    public String getFlickrToken() {
        return flickrToken;
    }

    public void setFlickrNsid(String flickerNsid) {
        this.flickrNsid = flickerNsid;
    }

    public String getFlickrNsid() {
        return flickrNsid;
    }

}
