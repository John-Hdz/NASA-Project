
package com.example.nasaproject.Modelos.LibraryImage;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LibraryJgason {

    @SerializedName("collection")
    @Expose
    public Collection collection;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    @Override
    public String toString()
    {
//        StringBuilder sb = new StringBuilder();
//        sb.append(LibraryJgason.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
//        sb.append("collection");
//        sb.append('=');
//        sb.append(((this.collection == null)?"<null>":this.collection));
//        sb.append(',');
//        if (sb.charAt((sb.length()- 1)) == ',') {
//            sb.setCharAt((sb.length()- 1), ']');
//        } else {
//            sb.append(']');
//        }
//        return sb.toString();
        return getCollection().getItems().getFirst().getData().getFirst().getMediaType();
    }

}
