/**
 * 
 */
package com.tmall.vo;

import java.util.List;

import com.tmall.model.Picture;

/**
 * @author Administrator
 *
 */
public class PictureVO
{
	private String productId;
	private List<Picture> detailPictures;
	private List<Picture> showPictures;
	private List<Picture> topPicture;
	private Picture indexPicture;

	public List<Picture> getDetailPictures()
	{
		return detailPictures;
	}

	public void setDetailPictures(List<Picture> detailPictures)
	{
		this.detailPictures = detailPictures;
	}

	public List<Picture> getShowPictures()
	{
		return showPictures;
	}

	public void setShowPictures(List<Picture> showPictures)
	{
		this.showPictures = showPictures;
	}

	public List<Picture> getTopPicture()
	{
		return topPicture;
	}

	public void setTopPicture(List<Picture> topPicture)
	{
		this.topPicture = topPicture;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public Picture getIndexPicture()
	{
		return indexPicture;
	}

	public void setIndexPicture(Picture indexPicture)
	{
		this.indexPicture = indexPicture;
	}

}
