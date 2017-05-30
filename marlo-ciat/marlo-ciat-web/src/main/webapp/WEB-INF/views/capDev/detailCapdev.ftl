[#ftl]

[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign customCSS = ["${baseUrl}/css/capDev/capacityDevelopment.css"] /]
[#assign customJS = ["${baseUrl}/js/capDev/capacityDevelopment.js"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]



<script src="${baseUrl}/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${baseUrl}/js/capDev/capacityDevelopment.js"></script>


<div class="container"> 
	<div class="col-md-3 capDevMenu">
		[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
	</div>
	
	
	
	[@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
	
	<div class="col-md-9 ">
	
	<div class="col-md-12">
			New Capacity Development		
	</div>
	
	<div class="col-md-12 form-group newCapdevForm"> 

		<!-- Title-->
		<div class="row newCapdevField">
			<div class="col-md-12 "> 
				<div class=""> 
				[@customForm.input name="capdev.title" type="text" i18nkey="capdev.form.title"  required=true  /]
				</div>
			</div>
		</div>


		<!-- Radio Buttons-->
		<div class="row newCapdevField"> 
			<div class="col-md-12 newCapdevField">
				[@s.text name="Category"][/@s.text] 
			</div>
			<div class="col-md-12">
				<div class="col-md-3">
					<div class="radio">
					  <label><input  id="individual" type="radio"  name="optradio" class="radioButton" value="individual">Individual</label>
					</div>
				</div>
				<div class="col-md-3">
					<div class="radio">
					  <label><input id="gruops" type="radio" name="optradio" class="radioButton" value="gruops">Groups</label>
					</div>
				</div>
				
			</div>
		</div>

		<!-- type-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 "> 
				[@customForm.select name="capdev.capdevType" listName="types" i18nkey="capdev.form.type"  placeholder="select option..." required=true /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="" i18nkey="capdev.form.contactPerson" type="text"   required=true  /]
			</div>
			</div>
		</div>
		<!-- Strart date-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.input name="" i18nkey="capdev.form.startDate" type="text"   /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="" i18nkey="capdev.form.endDate" type="text"   /]
			</div>
			</div>
		</div>
		<!-- Country-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.country" placeholder="select option..."  /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="" i18nkey="capdev.form.region" type="text"     /]
			</div>
			</div>
		</div>
		<!-- research Area-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.researchArea" placeholder="select option..."  /]
			</div>
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.researchProgram" placeholder="select option..."  /]
			</div>
			</div>
		</div>
		<!-- project-->
		<div class="row newCapdevField">
			<div class="col-md-12 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.project" placeholder="select option..."  /]
			</div>
		</div>
		<!-- CRP and Dictated by-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.crp" placeholder="select option..."  /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="" i18nkey="capdev.form.dictated" type="text"  /]
			</div>
			</div>
		</div>

		<!-- Targeted public-->
		<div class="row">
			<div class="col-md-12 newCapdevField approachesListTitle">
				[@s.text name="Targeted Public"][/@s.text] 
			</div>

		</div>

		<div class="row borderContainer">

			<div class="col-md-12 newCapdevField ">
				[#if targeted_public?has_content]
                    [#list targeted_public as approach]
                       
                    [/#list]
                 [/#if]
                 <p class="text-center inf" style="display:${(targeted_public?has_content)?string('none','block')}">[@s.text name="capdev.notDisciplines" /]</p>
			</div>
			<div class="col-md-12 newCapdevField ">
				[@customForm.select name="" listName="" i18nkey="Select a targeted public" className="disciplines" multiple=false placeholder="select option..."  /]
			</div>

		</div>

		<!-- Disciplines-->
		<div class="row ">
			<div class="col-md-12 newCapdevField approachesListTitle">
				[@s.text name="capdev.form.listOfApproaches"][/@s.text] 
			</div>
		</div>
		<div class="row approachesListContainer">
			<div class="col-md-12 newCapdevField approachesList">
				[#if capDevApproaches?has_content]
                    [#list capDevApproaches as approach]
                      [@disciplineMacro approach /]  
                    [/#list]
                 [/#if]
                 <p class="text-center inf" style="display:${(capDevApproaches?has_content)?string('none','block')}">[@s.text name="capdev.notDisciplines" /]</p>
			</div>
			<div class="col-md-12 newCapdevField">
				[@customForm.select name="" listName="approaches" i18nkey="capdev.form.selectApproach" className="disciplines" multiple=false placeholder="select option..."  /]
			</div>
		</div>


		<!-- OutComes-->
		<div class="row">
			<div class="col-md-12 newCapdevField objectivesTitle">
				[@s.text name="capdev.form.objectives"][/@s.text]
			</div>
		</div>
		
		<div class="row outComesContainer">
			<div class="col-md-12 newCapdevField outComesList">
				
				[#if capDevOutcomes?has_content]
                    [#list capDevOutcomes as outcome]
                      [@outComeMacro outcome /]  
                    [/#list]
                 [/#if]

                 <p class="text-center inf" style="display:${(capDevOutcomes?has_content)?string('none','block')}">[@s.text name="capdev.notObjectives" /]</p>
			</div>
			<div class="col-md-12 newCapdevField">
				[@customForm.select name="" listName="outcomes" i18nkey="capdev.form.selectOutcome" className="outComes" multiple=false placeholder="select option..."  /]
			</div>
		</div>


		<!-- participants-->
		<div class="row grupsParticipantsForm">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6  participantsTitle">
				[@s.text name="capdev.form.participants"][/@s.text]
			</div>
			<div class="col-md-6 ">
				<div class="pull-right">
					<button type="button" class="" aria-label="Left Align" >
	  				Dowmload template
					</button>
				</div>
			</div>
			</div>

			<div class="row newCapdevField">
				<div class="col-md-12 newCapdevField participantsBox">
					[@customForm.inputFile name="" /]
				</div>
			</div>
		</div>

		

		<!-- induvidual participant-->

		<div class="row newCapdevField individualparticipantForm" style="display:none;">
			<div class="col-md-12">
				<div class="col-md-6  participantsTitle">
				[@s.text name="Participant"][/@s.text]
			</div>
			</div>
			<div class="col-md-12">
				[@customForm.input name="" i18nkey="code" type="text" required=true /]
			</div>
			<div class="col-md-12">
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="First name" type="text" required=true /]
				</div>
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Last name" type="text" required=true /]
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Gender" type="text" required=true /]
				</div>
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="citizenship" type="text" required=true /]
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Country of residence" type="text" required=true /]
				</div>
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Highest degree" type="text" required=true /]
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Institution" type="text" required=true /]
				</div>
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Email" type="text" required=true /]
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Reference" type="text" required=true /]
				</div>
				<div class="col-md-6">
					[@customForm.input name="" i18nkey="Fellowship" type="text"  required=true /]
				</div>
			</div>




			

		</div>

		<!-- Deliverables -->
		<div class="row">
			<div class="col-md-12 newCapdevField deliverablesTitle">
				Supporting Documents
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 newCapdevField deliverablesContainer">
				[#if deliverables?has_content]
					[#list deliverables as deliverable]

					[/#list]
				[/#if]

				<p class="text-center inf" style="display:${(deliverables?has_content)?string('none','block')}">[@s.text name="capdev.notDeliverables" /]</p>

			</div>
			<div class="col-md-12 newCapdevField">
				<div class="pull-right">
					<div class="buttons-content">        
			          	<a class="addButton" href="[@s.url action='${centerSession}/addDeliverable' ][/@s.url]">[@s.text name="Add deliverable" /]</a>
			        	<div class="clearfix"></div>
		        	</div>
				</div>
			</div>
		</div>


		
		<!-- buttons -->
			<div class="col-md-12">
				<div class="col-md-6">
					<div class="pull-right">
						[@s.submit type="button" name="save" cssClass="button-save"]<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> [@s.text name="Save" /] [/@s.submit]
					</div>
				</div>

				<div class="col-md-6">
					<div class="pull-left">
						[@s.submit type="button"  name="cancel" cssClass="button-cancel"]<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> [@s.text name="Cancel" /] [/@s.submit]
					</div>
				</div>
				
			</div>
		
		</div>

	</div>
	
	[/@s.form]
	

</div>


[@objectiveMacro element={} index=0 isTemplate=true /] 
[@disciplineMacro element="" isTemplate=true/]
[@outComeMacro element="" isTemplate=true/]

[#include "/WEB-INF/global/pages/footer.ftl"]



[#macro objectiveMacro element index=0 isTemplate=false]
	
	<div id="objective-${isTemplate?string('template',(element.id)!)}" class="objective  borderBox row"  style="display:${isTemplate?string('none','block')}">
		<div class="removeObjective removeIcon" title="Remove objective"></div>
		<div class="col-md-12">
			 [@customForm.input name="objectiveBody" i18nkey="Objective # ${index + 1}" type="text" /]
		</div>

	</div>
	
[/#macro]


[#macro disciplineMacro element isTemplate=false]
	<div id="approach-${isTemplate?string('template',(element)!)}" class="approach  borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeDiscipline removeIcon" title="Remove approach"></div>
		<div class="col-md-4">
			 [@s.text name="element" /]
		</div>
	</div>
[/#macro]


[#macro outComeMacro element isTemplate=false]
	<div id="outcome-${isTemplate?string('template',(element)!)}" class="outcome  borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeOutCome removeIcon" title="Remove outcome"></div>
		<div class="col-md-4">
			 [@s.text name="element" /]
		</div>
	</div>
[/#macro]