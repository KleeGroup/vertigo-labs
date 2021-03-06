/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.labs.gedcom;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.geo.geocoder.GeoLocation;

public final class Individual implements DtObject {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", required = true, label = "identifiant")
	private String id;
	@Field(domain = "DO_LIBELLE", required = true, label = "Name")
	private String name;
	@Field(domain = "DO_LIBELLE", required = true, label = "Prénoms")
	private String givenName;
	@Field(domain = "DO_LIBELLE", required = true, label = "Nom de famille")
	private String surName;
	@Field(domain = "DO_LIBELLE", required = true, label = "Date de naissance")
	private String birthDate;
	@Field(domain = "DO_LIBELLE", required = true, label = "Lieu de Naissance")
	private String birthPlace;

	@Field(domain = "DO_LIBELLE", required = true, label = "Date de décès")
	private String deathDate;
	@Field(domain = "DO_LIBELLE", required = true, label = "Lieu de décès")
	private String deathPlace;

	@Field(domain = "DO_CODE", required = true, label = "Sexe")
	private String sex;

	//	Option<Individual> getFather(Individual individual);
	//
	//	Option<Individual> getMother(Individual individual);
	//-----
	private GeoLocation location;

	public void setId(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setGivenName(final String givenName) {
		this.givenName = givenName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setSurName(final String surName) {
		this.surName = surName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSex(final String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setBirthDate(final String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	@Field(domain = "DO_INTEGER", type = "COMPUTED", required = true, label = "Annee")
	public Integer getBirth() {
		return GedcomUtil.findYear(getBirthDate());
	}

	public void setBirthPlace(final String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setLocation(final GeoLocation location) {
		this.location = location;
	}

	public GeoLocation getLocation() {
		return location;
	}

	@Field(domain = "DO_CODE", type = "COMPUTED", required = true, label = "Département")
	public String getDepartement() {
		return location == null ? null : location.getLevel2();
	}

	//	@Field(domain = "DO_CODE", type = "COMPUTED", notNull = true, label = "Département")
	//	public String getDepartement() {
	//		return location == null ? null : location.getLevel1() + '/' + location.getLevel2();
	//	}

	public void setDeathDate(final String deathDate) {
		this.deathDate = deathDate;
	}

	public void setDeathPlace(final String deathPlace) {
		this.deathPlace = deathPlace;
	}
}
