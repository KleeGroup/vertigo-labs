package snowblood.gen.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est g�n�r�e automatiquement !
 * Objet de donn�es DbFileInfo
 */
@DtDefinition
public final class JobFileInfo implements DtObject {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long filId;
	private String fileName;
	private String mimeType;
	private Long length;
	private java.util.Date lastModified;
	private String filePath;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'Identifiant'. 
	 * @return Long filId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "Identifiant")
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
	@Field(domain = "DO_LIBELLE_COURT", notNull = true, label = "Nom")
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
	@Field(domain = "DO_LIBELLE_COURT", notNull = true, label = "Type mime")
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
	@Field(domain = "DO_FILE_SIZE", notNull = true, label = "Taille")
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
	@Field(domain = "DO_DATE_HEURE", notNull = true, label = "Date de derni�re modification")
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
	 * Champ : PATH.
	 * Récupère la valeur de la propriété 'data'. 
	 * @return String fileData 
	 */
	@Field(domain = "DO_FICHIER", notNull = true, label = "file path")
	public final String getFilePath() {
		return filePath;
	}

	/**
	 * Champ : FILE_PATH.
	 * Définit la valeur de la propriété 'path'.
	 * @param filePath String 
	 */
	public final void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	//Aucune Association d�clar�e

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
