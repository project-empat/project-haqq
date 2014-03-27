/**
 * 
 */
package slab.haqq.lib.adapter.model;

import java.util.Locale;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author rasxen
 * A sura data model
 * Implement a {@link Parcelable} to pass this model between {@link Activity}
 */
public class Sura implements Parcelable {
	private String id;
	private int idn;
	private String name;
	private String arname;
	private int ayaCount;
	private String slug;

	/**
	 * A sura model constructor
	 * @param id
	 * @param idn
	 * @param name
	 * @param ayaCount
	 */
	public Sura(String id, int idn, String name, String arname, int ayaCount) {
		Locale l = Locale.getDefault();
		this.id = id;
		this.idn = idn;
		this.name = name;
		this.arname = arname;
		this.slug = name.toLowerCase(l).replaceAll("\\P{L}+", "");
		this.ayaCount = ayaCount;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ayaCount
	 */
	public int getAyaCount() {
		return ayaCount;
	}

	/**
	 * @param ayaCount
	 *            the ayaCount to set
	 */
	public void setAyaCount(int ayaCount) {
		this.ayaCount = ayaCount;
	}

	/**
	 * @return the idn
	 */
	public int getIdn() {
		return idn;
	}

	/**
	 * @param idn
	 *            the idn to set
	 */
	public void setIdn(int idn) {
		this.idn = idn;
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @param slug
	 *            the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * @return the arname
	 */
	public String getArname() {
		return arname;
	}

	/**
	 * @param arname
	 *            the arname to set
	 */
	public void setArname(String arname) {
		this.arname = arname;
	}

	/**
	 * A sura model constructor with a parcel as its parameter
	 * @param in
	 */
	public Sura(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.arname = in.readString();
		this.slug = in.readString();
		this.idn = in.readInt();
		this.ayaCount = in.readInt();
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(arname);
		dest.writeString(slug);
		dest.writeInt(idn);
		dest.writeInt(ayaCount);
	}

	public static Parcelable.Creator<Sura> CREATOR = new Creator<Sura>() {

		@Override
		public Sura[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Sura[size];
		}

		@Override
		public Sura createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Sura(source);
		}
	};
}
