package com.getarticleinfo.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class StoresList extends ArrayList<Store> implements Parcelable {

	private static final long serialVersionUID = 663585476779879096L;

	public StoresList() {

	}

	public StoresList(Parcel in) {

		readFromParcel(in);

	}

	private void readFromParcel(Parcel in) {

		this.clear();

		// First we have to read the list size
		int size = in.readInt();

		// Reading remember that we wrote first the Name and later the Phone
		// Number.
		// Order is fundamental
		for (int i = 0; i < size; i++) {
			Store s = new Store();
			s.setName(in.readString());
			s.setNumber(in.readString());
			s.setWwsEndpoint(in.readString());
			s.setActive(in.readString());
			this.add(s);
		}

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		int size = this.size();

		// We have to write the list size, we need him recreating the list

		dest.writeInt(size);

		// We decided arbitrarily to write first the Name and later the Phone
		// Number.

		for (int i = 0; i < size; i++) {
			Store s = this.get(i);
			dest.writeString(s.getName());
			dest.writeString(s.getNumber());
			dest.writeString(s.getWwsEndpoint());
			dest.writeString(s.getActive());
		}
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public StoresList createFromParcel(Parcel in) {
			return new StoresList(in);
		}

		public Object[] newArray(int arg0) {
			return null;
		}
	};

}
