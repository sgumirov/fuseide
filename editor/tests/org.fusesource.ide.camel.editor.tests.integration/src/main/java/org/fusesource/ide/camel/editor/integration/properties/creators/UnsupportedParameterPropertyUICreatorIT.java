/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.fusesource.ide.camel.editor.integration.properties.creators;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Text;
import org.fusesource.ide.camel.editor.properties.creators.advanced.UnsupportedParameterPropertyUICreatorForAdvanced;
import org.fusesource.ide.camel.editor.properties.creators.details.UnsupportedParameterPropertyUICreatorForDetails;
import org.fusesource.ide.camel.model.service.core.catalog.Parameter;
import org.fusesource.ide.camel.model.service.core.catalog.eips.Eip;
import org.fusesource.ide.camel.model.service.core.util.PropertiesUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Aurelien Pupier
 *
 */
public class UnsupportedParameterPropertyUICreatorIT extends AbstractParameterPropertySectionUICreatorITHelper {

	private Parameter parameter;
	private Eip eip;

	@Before
	public void setup() {
		parameter = new Parameter();
		parameter.setName("testParameterName");
		parameter.setKind("parameter");
		parameter.setJavaType("unsupportedJavaType");
		eip = new Eip();
		final ArrayList<Parameter> parameters = new ArrayList<>();
		parameters.add(parameter);
		eip.setParameters(parameters);
	}

	@Test
	public void testUIDisplayedForDetails() throws Exception {
		final UnsupportedParameterPropertyUICreatorForDetails unsupportedParameterPropertyUICreator = new UnsupportedParameterPropertyUICreatorForDetails(dbc, modelMap, eip, camelModelElement, parameter, parent, widgetFactory);
		unsupportedParameterPropertyUICreator.create();

		final Text control = unsupportedParameterPropertyUICreator.getControl();
		control.setText("newValue");

		assertThat(modelMap.get("testParameterName")).isEqualTo("newValue");
		assertThat(camelModelElement.getParameter("testParameterName")).isEqualTo("newValue");
	}

	@Test
	public void testUIDisplayedForAdvanced() throws Exception {
		camelModelElement.setParameter("uri", "testUri");
		final UnsupportedParameterPropertyUICreatorForAdvanced unsupportedParameterPropertyUICreator = new UnsupportedParameterPropertyUICreatorForAdvanced(dbc, modelMap, eip,
				camelModelElement, parameter, parent, widgetFactory);
		unsupportedParameterPropertyUICreator.create();

		final Text control = unsupportedParameterPropertyUICreator.getControl();
		control.setText("newValue");

		assertThat(modelMap.get("testParameterName")).isEqualTo("newValue");
		assertThat(PropertiesUtils.getPropertyFromUri(camelModelElement, parameter, PropertiesUtils.getComponentFor(camelModelElement))).isEqualTo("newValue");
	}

}
