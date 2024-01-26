<%
/**
 * @ (#) dentalDetails.jsp Aug 28 2017
 * Project       : TTK HealthCare Services
 * File          : dentalDetails.jsp
 * Author        : Kishor kumar S H
 * Company       : TTK HealthCare Services
 * Date Created  : Aug 28 2017
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/providerLogin/dentalValidation.js"></SCRIPT>
<table id="orthoDiv" style="display: none;">
			  <tr> <td class="formLabel">Dento Class</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.dentoclass1" value="C1"/>  Class I
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.dentoclass2" value="C2"/>  Class II D <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.dentoclass2Text"/>
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.dentoclass3" value="C3"/>  Class III <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.dentoclass3Text"/>
					</td> 
			</tr>
			<tr> <td class="formLabel">Skeletal Class</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.skeletalClass1" value="S1"/>  Class I
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.skeletalClass2" value="S2"/>  Class II
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.skeletalClass3" value="S3"/>  Class III 
					</td> 
			</tr> 
			<tr> <td class="formLabel">Overjet</td>
					<td class="textLabel">: 
					&nbsp;&nbsp;&nbsp; 
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.overJet" styleId="overJet" 
					onblur="checkDentoCombo(this,'Overjet')" />mm
					</td> 
			</tr>
			<tr> <td class="formLabel">Reverse Overjet</td>
					<td class="textLabel">: 
					&nbsp;&nbsp;&nbsp; 
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.reverseJet" 
					styleId="reverseJet" onblur="checkDentoCombo(this,'ReverseJet')" />mm<br>
					</td>
			</tr>
			<tr> 	<td class="formLabel"> &nbsp;</td><td class="formLabel">
					&nbsp;&nbsp;&nbsp;&nbsp;  With speech/Masticatory difficulty<br>
					</td> 
			</tr>
			<tr> 	<td class="formLabel"> &nbsp;</td><td class="formLabel">
					&nbsp;&nbsp;&nbsp;  <html:radio property="dentalOrthoVO.reverseJetYN" value="Y"/> Yes 
					&nbsp;&nbsp; <html:radio property="dentalOrthoVO.reverseJetYN" value="N"/> No
					</td> 
			</tr>
			<tr> <td class="formLabel">Crossbite</td>
					<td class="textLabel">: 
					&nbsp;&nbsp;  <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbiteAntrio" styleId="crossbiteAntrio" 
					onblur="checkCrossBite(this,'Crossbite')"/>
					<a href="#" onClick="openListIntX('crossbiteAntrio','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbiteAntriomm" styleId="crossbiteAntriomm"/>
					mm, Anterior
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbitePosterior" styleId="crossbitePosterior"
					onblur="checkCrossBite(this,'Crossbite')"/>
					<a href="#" onClick="openListIntX('crossbitePosterior','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbitePosteriormm" styleId="crossbitePosteriormm"/>
					mm, Posterior
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp;  <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbiteRetrucontract" styleId="crossbiteRetrucontract"
					onblur="checkCrossBite(this,'Crossbite')"/>
					<a href="#" onClick="openListIntX('crossbiteRetrucontract','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.crossbiteRetrucontractmm" styleId="crossbiteRetrucontractmm"/>
					mm, between retruded contact position and intercuspal position
					</td>
			</tr>
			
			<tr> <td class="formLabel">Openbite</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.openbiteAntrio" 
					styleId="openbiteAntrio"
					onblur="checkDentoCombo(this,'Open Bite')" />mm, Anterior
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp;  <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.openbitePosterior"
					styleId="openbitePosterior"
					onblur="checkDentoCombo(this,'Open Bite')"/>mm, Posterior
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp;  <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.openbiteLateral"
					styleId="openbiteLateral"
					onblur="checkDentoCombo(this,'Open Bite')"/>mm, Lateral
					</td>
			</tr>
			
			
			<tr> <td class="formLabel">Contact Point displacement</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.contactPointDisplacement" styleId="contactPointDisplacement"
					onblur="checkCrossBite(this,'Displacement')"/>
					<a href="#" onClick="openListIntX('contactPointDisplacement','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					&nbsp; <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.contactPointDisplacementmm" styleId="contactPointDisplacementmm"/> mm
					</td>
			</tr>
			
			<tr> <td class="formLabel">Overbite</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:radio property="dentalOrthoVO.overBite" styleId="overBiteD" value="D" onclick="checkDentoCombo(this,'Over Bite')"/>  Deep
								 <html:radio property="dentalOrthoVO.overBite" styleId="overBiteC" value="C" onclick="checkDentoCombo(this,'Over Bite')"/>  Complete
								<html:radio property="dentalOrthoVO.overBite" styleId="overBiteI" value="I" onclick="checkDentoCombo(this,'Over Bite')"/>  Incomplete
								<html:radio property="dentalOrthoVO.overBite" styleId="overBiteN" value="N" onclick="checkDentoCombo(this,'Over Bite')"/>  None
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp; With palatal trauma &nbsp;&nbsp; <html:radio property="dentalOrthoVO.overbitePalatalYN" value="Y"/> Yes &nbsp;&nbsp; <html:radio property="dentalOrthoVO.overbitePalatalYN" value="N"/> No
					</td> 
			</tr><tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp; With Gingival trauma&nbsp;&nbsp; <html:radio property="dentalOrthoVO.overbiteGingivalYN" value="Y"/> Yes &nbsp;&nbsp; <html:radio property="dentalOrthoVO.overbiteGingivalYN" value="N"/> No
					</td>
			</tr>
			
			<%-- <tr> <td class="formLabel">Hypodontia</td>
					<td class="textLabel">: 
					&nbsp;&nbsp; <html:checkbox property="dentalOrthoVO.dentoclass2"/>  None
			</tr> --%>
			<tr> <td class="formLabel">Hypodontia</td>
					<td class="textLabel">:
					&nbsp; Quandrant 1, teeth #: 
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.hypodontiaQuand1Teeth" styleId="hypodontiaQuand1Teeth" 
					onblur="checkToothNo(this,'Hypodontia')"/>
					<a href="#" onClick="openListIntX('hypodontiaQuand1Teeth','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					
				</td>
			</tr><tr> <td class="formLabel">&nbsp;</td>			
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;  Quandrant 2, teeth #: <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.hypodontiaQuand2Teeth" styleId="hypodontiaQuand2Teeth"
					onblur="checkToothNo(this,'Hypodontia')"/>
					<a href="#" onClick="openListIntX('hypodontiaQuand2Teeth','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
				</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp; Quandrant 3, teeth #: <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.hypodontiaQuand3Teeth" styleId="hypodontiaQuand3Teeth"
					onblur="checkToothNo(this,'Hypodontia')"/>
					<a href="#" onClick="openListIntX('hypodontiaQuand3Teeth','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr><tr> <td class="formLabel">&nbsp;</td>			
					<td class="textLabel"> &nbsp;&nbsp;&nbsp;&nbsp;Quandrant 4, teeth #: <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.hypodontiaQuand4Teeth" styleId="hypodontiaQuand4Teeth"
					onblur="checkToothNo(this,'Hypodontia')"/>
					<a href="#" onClick="openListIntX('hypodontiaQuand4Teeth','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a></td>
			</tr>
			
			<tr> <td class="formLabel">Others</td>
					<td class="textLabel">: 
					&nbsp; impeded teeth eruption, teeth #:<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.impededTeethEruptionNo" styleId="impededTeethEruptionNo"
					onblur="checkImpededTooth(this,'Impeded')"/>
					<a href="#" onClick="openListIntX('impededTeethEruptionNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr><tr> <td class="formLabel">&nbsp;</td>			
					<td class="textLabel"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;impacted teeth, teeth #:
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.impededTeethNo" styleId="impededTeethNo"
					onblur="checkImpededTooth(this,'Impacted')"/>
					<a href="#" onClick="openListIntX('impededTeethNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp; Submergerd teeth, teeth #: 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.submergerdTeethNo" styleId="submergerdTeethNo"
					onblur="checkImpededTooth(this,'Submerged')"/>
					<a href="#" onClick="openListIntX('submergerdTeethNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr><tr> <td class="formLabel">&nbsp;</td>			
					<td class="textLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Supernumerary teeth, teeth #: 
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.supernumeryTeethNo" styleId="supernumeryTeethNo"
					onblur="checkImpededTooth(this,'Supernumerary')"/>
					<a href="#" onClick="openListIntX('supernumeryTeethNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp;&nbsp; Retained teeth, teeth #: 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.retainedTeethNo" styleId="retainedTeethNo"/>
					<a href="#" onClick="openListIntX('retainedTeethNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
					</tr><tr> <td class="formLabel">&nbsp;</td>			
					<td class="textLabel">&nbsp;&nbsp;&nbsp;&nbsp; Ectopic teeth, teeth #: 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.ectopicTeethNo" styleId="ectopicTeethNo"
					onblur="checkImpededTooth(this,'Ectopic')"/>
					<a href="#" onClick="openListIntX('ectopicTeethNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
			</tr>
			<tr> <td class="formLabel">&nbsp;</td>
					<td class="textLabel">
					&nbsp;&nbsp;&nbsp; Cranio-facial anomaly, please specify: 
					<br>&nbsp;&nbsp;&nbsp; <html:textarea property="dentalOrthoVO.cranioFacialNo" cols="35" rows="2" />
					</td>					
			</tr>
			<tr>
				<td>	&nbsp;	</td>
			</tr>
			<tr><td>	&nbsp;	</td>
				<td class="textLabel">	&nbsp;&nbsp;&nbsp;<b>AESTHETIC COMPONENT (AC)-</b> MARK	</td>
			</tr>
			<tr><td>	&nbsp;	</td>
				<td class="textLabel">	&nbsp;&nbsp;&nbsp;<b>Select</b> appropriate score:  	</td>
			</tr>
			<tr><td>	&nbsp;	</td>
				<td class="textLabel"> &nbsp;&nbsp;&nbsp; 
				<table>
					<tr>
					<td> <span class="smallCircle">&nbsp;1</span><html:radio property="dentalOrthoVO.aestheticComp" value="1"/>    </td>
					<td> <span class="smallCircle">&nbsp;2</span><html:radio property="dentalOrthoVO.aestheticComp" value="2"/>    </td>
					<td> <span class="smallCircle">&nbsp;3</span><html:radio property="dentalOrthoVO.aestheticComp" value="3"/>    </td>
					<td> <span class="smallCircle">&nbsp;4</span><html:radio property="dentalOrthoVO.aestheticComp" value="4"/>    </td>
					<td> <span class="smallCircle">&nbsp;5</span><html:radio property="dentalOrthoVO.aestheticComp" value="5"/>    </td>
					<td> <span class="smallCircle">&nbsp;6</span><html:radio property="dentalOrthoVO.aestheticComp" value="6"/>    </td>
					<td> <span class="smallCircle">&nbsp;7</span><html:radio property="dentalOrthoVO.aestheticComp" value="7"/>    </td>
					<td> <span class="smallCircle">&nbsp;8</span><html:radio property="dentalOrthoVO.aestheticComp" value="8"/>    </td>
					<td> <span class="smallCircle">&nbsp;9</span><html:radio property="dentalOrthoVO.aestheticComp" value="9"/>    </td>
					<td> <span class="smallCircle">&nbsp;10</span><html:radio property="dentalOrthoVO.aestheticComp" value="10"/>    </td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td>	&nbsp;	</td>
				<td class="textLabel">	&nbsp;&nbsp;&nbsp;<b>IOTN Score - </b>
				 <html:text styleClass="textBox textBoxVerySmall" property="dentalOrthoVO.iotn" readonly="true"/>
				 </td>
			</tr>
			</table>