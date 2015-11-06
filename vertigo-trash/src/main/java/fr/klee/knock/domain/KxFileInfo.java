package fr.klee.knock.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est g�n�r�e automatiquement !
 * Objet de donn�es AbstractKxFileInfo
 */
@DtDefinition
public final class KxFileInfo implements DtObject {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long filId;
	private String fileName;
	private String mimeType;
	private Long length;
	private java.util.Date lastModified;
	private String fileData;

	/**
	 * Champ : PRIMARY_KEY.
	 * R�cup�re la valeur de la propri�t� 'Identifiant'. 
	 * @return Long filId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_GED_URI", type = "PRIMARY_KEY", required = true, label = "Identifiant")
	public final Long getFilId() {
		return filId;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * D�finit la valeur de la propri�t� 'Identifiant'.
	 * @param filId Long <b>Obligatoire</b>
	 */
	public final void setFilId(final Long filId) {
		this.filId = filId;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Nom'. 
	 * @return String fileName <b>Obligatoire</b>
	 */
	@Field(domain = "DO_GED_LABEL", required = true, label = "Nom")
	public final String getFileName() {
		return fileName;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Nom'.
	 * @param fileName String <b>Obligatoire</b>
	 */
	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Type mime'. 
	 * @return String mimeType <b>Obligatoire</b>
	 */
	@Field(domain = "DO_GED_LABEL", required = true, label = "Type mime")
	public final String getMimeType() {
		return mimeType;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Type mime'.
	 * @param mimeType String <b>Obligatoire</b>
	 */
	public final void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Taille'. 
	 * @return Long length <b>Obligatoire</b>
	 */
	@Field(domain = "DO_GED_SIZE", required = true, label = "Taille")
	public final Long getLength() {
		return length;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Taille'.
	 * @param length Long <b>Obligatoire</b>
	 */
	public final void setLength(final Long length) {
		this.length = length;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Date de derni�re modification'. 
	 * @return java.util.Date lastModified <b>Obligatoire</b>
	 */
	@Field(domain = "DO_GED_DATE", required = true, label = "Date de derni�re modification")
	public final java.util.Date getLastModified() {
		return lastModified;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Date de derni�re modification'.
	 * @param lastModified java.util.Date <b>Obligatoire</b>
	 */
	public final void setLastModified(final java.util.Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'data'. 
	 * @return String fileData 
	 */
	@Field(domain = "DO_GED_LABEL", persistent = false, label = "data")
	public final String getFileData() {
		return fileData;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'data'.
	 * @param fileData String 
	 */
	public final void setFileData(final String fileData) {
		this.fileData = fileData;
	}

	//Aucune Association d�clar�e

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
