/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator;

import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.DrugOrdersCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.data.patient.PatientData;
import org.openmrs.module.reporting.data.patient.definition.DrugOrdersForPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.service.PatientDataService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Evaluator for drug orders based cohorts
 */
@Handler(supports = DrugOrdersCohortDefinition.class)
public class DrugOrdersCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		DrugOrdersCohortDefinition drugOrdersCohortDefinition = (DrugOrdersCohortDefinition) cohortDefinition;

		DrugOrdersForPatientDataDefinition drugOrdersForPatientDataDefinition = new DrugOrdersForPatientDataDefinition();
		drugOrdersForPatientDataDefinition.setDrugConceptsToInclude(drugOrdersCohortDefinition.getConceptList());
		Set<Integer> patientIds = new HashSet<Integer>();
		PatientData patientData = Context.getService(PatientDataService.class).evaluate(drugOrdersForPatientDataDefinition, context);
		for (Map.Entry<Integer, Object> d :patientData.getData().entrySet()) {
			patientIds.add(d.getKey());
		}

		return new EvaluatedCohort(new Cohort(patientIds), drugOrdersCohortDefinition, context);
	}

}
