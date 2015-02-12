package zzzz;


import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est g�n�r�e automatiquement !
 * Objet de donn�es AbstractDocumentIndexed
 */
@DtDefinition(persistent = false)
public final class DocumentIndexed implements DtObject {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String docUrl;
	private String docName;
	private String customer;
	private String project;
	private String title;
	private String content;
	private java.util.Date lastModified;
	private Long size;
	private String type;
	private String channel;

	/**
	 * Champ : PRIMARY_KEY.
	 * R�cup�re la valeur de la propri�t� 'URL du document'. 
	 * @return String docUrl <b>Obligatoire</b>
	 */
	@Field(domain = "DO_DOC_URL", type = "PRIMARY_KEY", notNull = true, persistent = false, label = "URL du document")
	public final String getDocUrl() {
		return docUrl;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * D�finit la valeur de la propri�t� 'URL du document'.
	 * @param docUrl String <b>Obligatoire</b>
	 */
	public final void setDocUrl(final String docUrl) {
		this.docUrl = docUrl;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Nom du document'. 
	 * @return String docName 
	 */
	@Field(domain = "DO_DOC_TITLE", persistent = false, label = "Nom du document")
	public final String getDocName() {
		return docName;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Nom du document'.
	 * @param docName String 
	 */
	public final void setDocName(final String docName) {
		this.docName = docName;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Nom du client'. 
	 * @return String customer 
	 */
	@Field(domain = "DO_DOC_TITLE", persistent = false, label = "Nom du client")
	public final String getCustomer() {
		return customer;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Nom du client'.
	 * @param customer String 
	 */
	public final void setCustomer(final String customer) {
		this.customer = customer;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Nom du projet'. 
	 * @return String project 
	 */
	@Field(domain = "DO_DOC_TITLE", persistent = false, label = "Nom du projet")
	public final String getProject() {
		return project;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Nom du projet'.
	 * @param project String 
	 */
	public final void setProject(final String project) {
		this.project = project;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Titre'. 
	 * @return String title 
	 */
	@Field(domain = "DO_DOC_TITLE", persistent = false, label = "Titre")
	public final String getTitle() {
		return title;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Titre'.
	 * @param title String 
	 */
	public final void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Contenu'. 
	 * @return String content 
	 */
	@Field(domain = "DO_DOC_CONTENT", persistent = false, label = "Contenu")
	public final String getContent() {
		return content;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Contenu'.
	 * @param content String 
	 */
	public final void setContent(final String content) {
		this.content = content;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Date de mise � jour'. 
	 * @return java.util.Date lastModified 
	 */
	@Field(domain = "DO_DOC_LAST_MODIFIED", persistent = false, label = "Date de mise � jour")
	public final java.util.Date getLastModified() {
		return lastModified;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Date de mise � jour'.
	 * @param lastModified java.util.Date 
	 */
	public final void setLastModified(final java.util.Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Taille'. 
	 * @return Long size 
	 */
	@Field(domain = "DO_DOC_SIZE", persistent = false, label = "Taille")
	public final Long getSize() {
		return size;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Taille'.
	 * @param size Long 
	 */
	public final void setSize(final Long size) {
		this.size = size;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Type de fichier'. 
	 * @return String type 
	 */
	@Field(domain = "DO_DOC_TYPE", persistent = false, label = "Type de fichier")
	public final String getType() {
		return type;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Type de fichier'.
	 * @param type String 
	 */
	public final void setType(final String type) {
		this.type = type;
	}

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Channel'. 
	 * @return String channel 
	 */
	@Field(domain = "DO_DOC_TYPE", persistent = false, label = "Channel")
	public final String getChannel() {
		return channel;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Channel'.
	 * @param channel String 
	 */
	public final void setChannel(final String channel) {
		this.channel = channel;
	}

	//Aucune Association d�clar�e

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
