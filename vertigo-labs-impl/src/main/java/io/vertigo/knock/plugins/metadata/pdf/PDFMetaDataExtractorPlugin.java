package io.vertigo.knock.plugins.metadata.pdf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.dynamo.file.util.FileUtil;
import io.vertigo.knock.impl.metadata.MetaDataExtractorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataSource;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.apache.pdfbox.preflight.utils.ByteArrayDataSource;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * @author  pchretien
 * @version $Id: PDFMetaDataExtractorPlugin.java,v 1.5 2014/02/27 10:22:04 pchretien Exp $
 */
public final class PDFMetaDataExtractorPlugin implements MetaDataExtractorPlugin {

	private static final String PDFA_VALID = "VALID";

	private static final String PDFA_INVALID = "INVALID";

	//	private final MetaDataNameSpace metaDataNameSpace;
	//
	//	/**
	//	 * Constructeur.
	//	 */
	//	public PDFMetaDataExtractorPlugin(final MetaDataManager metaDataManager) {
	//		Assertion.notNull(metaDataManager);
	//		//---------------------------------------------------------------------
	//		this.metaDataNameSpace = metaDataManager.getNameSpace();
	//	}

	private static PDDocument createPDDocument(final KFile file) throws IOException {
		try (final InputStream inputStream = file.createInputStream()) {
			return PDDocument.load(inputStream);
		}
	}

	/** {@inheritDoc} */
	public MetaDataContainer extractMetaData(final KFile file) throws Exception {
		Assertion.checkNotNull(file);
		//----------------------------------------------------------------------
		//Extraction de TOUT le contenu d'un pdf
		final PDDocument pdd = createPDDocument(file);
		try {
			final PDFTextStripper stripper = new PDFTextStripper();
			final String content = stripper.getText(pdd);
			final String pdfaValidationMsg = getPdfA1bValidation(file);
			//------------------------------------------------------------------
			//Metadata
			final PDDocumentInformation documentInformation = pdd.getDocumentInformation();
			return new MetaDataContainerBuilder()//
					.withMetaData(PDFMetaData.AUTHOR, documentInformation.getAuthor())//
					.withMetaData(PDFMetaData.KEYWORDS, documentInformation.getKeywords())//
					.withMetaData(PDFMetaData.SUBJECT, documentInformation.getSubject())//
					.withMetaData(PDFMetaData.TITLE, documentInformation.getTitle())//
					.withMetaData(PDFMetaData.CONTENT, content)//
					.withMetaData(PDFMetaData.PRODUCER, documentInformation.getProducer())//
					.withMetaData(PDFMetaData.PDFA, String.valueOf(PDFA_VALID.equals(pdfaValidationMsg)))//
					.withMetaData(PDFMetaData.PDFA_VALIDATION_MSG, pdfaValidationMsg)//
					.build();
			//metaDataContainer.setValue(PDFMetaData.SUMMARY, content.length() > 1000 ? content.substring(0, 1000) : content);
			//metaDataContainer.setValue(PDFMetaData.PRODUCER, documentInformation.getCreationDate().tProducer());
			//Autres m�ta donn�es non trait�es pour l'instant
			//documentInformation.getAuthor();
			//documentInformation.getProducer();
			//documentInformation.getCreationDate();
			//documentInformation.getModificationDate();
			//documentInformation.getProducer();
		} finally {
			if (pdd != null) {
				pdd.close();
			}
		}
	}

	private String getPdfA1bValidation(final KFile file) throws IOException {
		ValidationResult result;
		try (final InputStream inputStream = file.createInputStream()) {
			final DataSource ds = new ByteArrayDataSource(inputStream);
			final PreflightParser parser = new PreflightParser(ds);
			try {
				/* Parse the PDF file with PreflightParser that inherits from the NonSequentialParser.
				 * Some additional controls are present to check a set of PDF/A requirements. 
				 * (Stream length consistency, EOL after some Keyword...)
				 */
				parser.parse();
				/* Once the syntax validation is done, 
				 * the parser can provide a PreflightDocument 
				 * (that inherits from PDDocument) 
				 * This document process the end of PDF/A validation.
				 */
				final PreflightDocument document = parser.getPreflightDocument();
				document.validate();

				// Get validation result
				result = document.getResult();
				document.close();
			} catch (final SyntaxValidationException e) {
				/* the parse method can throw a SyntaxValidationException 
				 * if the PDF file can't be parsed.
				 * final In this case, the exception contains an instance of ValidationResult
				 */
				result = e.getResult();
			}
			// display validation result
			if (result.isValid()) {
				return PDFA_VALID;
			}
			final StringBuilder sb = new StringBuilder(PDFA_INVALID);
			sb.append(" : ");
			for (final ValidationError error : result.getErrorsList()) {
				sb.append("\n").append(error.getErrorCode()).append(" : ").append(error.getDetails()).append(",");
			}
			return sb.toString();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean accept(final KFile file) {
		Assertion.checkNotNull(file);
		//---------------------------------------------------------------------
		final String fileExtension = FileUtil.getFileExtension(file.getFileName());
		return "pdf".equalsIgnoreCase(fileExtension);
	}
}
