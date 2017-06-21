[#ftl]

[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign customCSS = ["${baseUrl}/css/capDev/capacityDevelopment.css"] /]
[#assign customJS = ["${baseUrl}/js/capDev/capacityDevelopment.js"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]





<script src="${baseUrl}/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${baseUrl}/js/capDev/capacityDevelopment.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.7.7/xlsx.core.min.js"></script>


<div class="container"> 
	<!-- <div class="col-md-3 capDevMenu">
		[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
	</div> -->
	
	
	
	
	
	<div class="col-md-12 ">
	
	<div class="col-md-12">
			New Capacity Development		
	</div>
	
	<div class="col-md-12 form-group newCapdevForm"> 

		[@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
		<!-- Radio Buttons-->
		<div class="row newCapdevField"> 
			<div class="col-md-12 newCapdevField">
				[@s.text name="Category" required=true ][/@s.text] 
				<p>you should select the capacity development intervention category</p>
			</div>
			<div class="col-md-12">
				<div class="col-md-3">
					<div class="radio">
					  <label><input  id="individual" type="radio"  name="capdev.category" class="radioButton" value="1">Individual</label>
					</div>
				</div>
				<div class="col-md-3">
					<div class="radio">
					  <label><input id="gruops" type="radio" name="capdev.category" class="radioButton" value="2">Groups</label>
					</div>
				</div>
				
			</div>
		</div>

		<div style="display:none" class="fullForm">
			<!-- Title-->
			<div class="row newCapdevField group individual" style="display:${(capdev.category == 1)?string('none','block')}">
				<div class="col-md-12 "> 
					<div class="" > 
						[@customForm.input name="capdev.title" type="text" i18nkey="capdev.form.title"  required=true  /]
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12 newCapdevField">
					<!-- type-->
					<div class="col-md-6 "> 
						[@customForm.select name="capdev.capdevType.id" listName="capdevTypes" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.type"  placeholder="select option..." required=true editable=true/]
					</div>

					<!-- Contact person -->
					<div class="col-md-6 contactField group individual">
						[@customForm.input name="" i18nkey="capdev.form.contactPerson" type="text" className='contact'  required=true  /]
						<input class="ctFirsName" type="hidden" name="capdev.ctFirstName" value="${(capdev.ctFirstName)!}" /> 
						<input class="ctLastName" type="hidden" name="capdev.ctLastName" value="${(capdev.ctLastName)!}" /> 
						<input class="ctEmail" type="hidden" name="capdev.ctEmail" value="${(capdev.ctEmail)!}" /> 
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 newCapdevField">
					<!-- Strart date-->
					<div class="col-md-6 ">
						[@customForm.input name="capdev.startDate" i18nkey="capdev.form.startDate" type="text" required=true  editable=true /]
					</div>
					<!-- end date-->
					<div class="col-md-6 ">
						[@customForm.input name="capdev.endDate" i18nkey="capdev.form.endDate" type="text"  editable=true /]
					</div>
				</div>
			</div>
			


			<div class="row">
				<div class="col-md-5 newCapdevField listContainerTitle">
					[@s.text name="Region(s)"][/@s.text] 
				</div>

				<div class="col-md-5 newCapdevField listContainerTitle">
					[@s.text name="County(s)"][/@s.text] 
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<!-- other regions-->
					<div class="col-md-5 listContainer">
						<div class="newCapdevField">
							[@customForm.select name="" listName="regionsList" keyFieldName="id" displayFieldName="name"  i18nkey="" className="capdevRegionsSelect" multiple=false placeholder="select option..."  /]
						</div>

						<div id="capdevRegionsList" class="newCapdevField regionsList">
							<ul class="list">
								[#if capdev.capDevRegions?has_content]
								[#list capdev.capDevRegions as region]
									<li id="" class="capdevRegion clearfix col-md-3">
										[#if editable ]
										<div class="removeRegion removeIcon" title="Remove region"></div>
										[/#if]
										<input class="id" type="hidden" name="capdev.capDevRegions[${region_index}].id" value="${(region.id)!-1}" />
										<input class="cId" type="hidden" name="capdev.capDevRegions[${country_index}].locElement.id" value="${(region.locElement.id)!}" />
										<span class="name"><span> <i class="flag-sm flag-sm-${(region.locElement.isoAlpha2)!}"></i> [@utilities.wordCutter string=(region.locElement.name)! maxPos=20 /]</span></span>
										<div class="clearfix"></div>
									</li>
									[/#list] 
									[#else]
									<p class="emptyText"> [@s.text name="No regions added yet." /]</p> 
									[/#if]
								</ul>

							</div>
						</div>

					<!-- other countries-->
					<div class="col-md-5 listContainer">

						<div class="newCapdevField">
							[@customForm.select name="" listName="countryList" keyFieldName="id" displayFieldName="name"  i18nkey="" className="capdevCountriesSelect" multiple=false placeholder="select option..."  /]
						</div>
						<div id="capdevCountriesList" class="newCapdevField countriesList">
							<ul class="list">
								[#if capdev.capDevCountries?has_content]
								[#list capdev.capDevCountries as country]
								<li id="" class="capdevCountry clearfix col-md-3">
									[#if editable ]
									<div class="removeCountry removeIcon" title="Remove country"></div>
									[/#if]
									<input class="id" type="hidden" name="capdev.capDevCountries[${country_index}].id" value="${(country.id)!-1}" />
									<input class="cId" type="hidden" name="capdev.capDevCountries[${country_index}].locElement.id" value="${(country.locElement.id)!}" />
									<span class="name"><span> <i class="flag-sm flag-sm-${(country.locElement.isoAlpha2)!}"></i> [@utilities.wordCutter string=(country.locElement.name)! maxPos=20 /]</span></span>
									<div class="clearfix"></div>
								</li>
								[/#list]
								[#else]
								<p class="emptyText"> [@s.text name="No countries added yet." /]</p> 
								[/#if]
							</ul>
						</div>
					</div>
				</div> 	
			</div>

			<!-- research Area-->
			<div class="row">
				<div class="col-md-12 newCapdevField">
					<div class="col-md-6 ">
						<!-- [@customForm.select name="capdev.researchArea.id" listName="researchAreas" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.researchArea" placeholder="select option..."  /] -->
						[@s.label key="capdev.form.researchArea" /]
						<select class="selectpicker" name="capdev.researchArea">
							<option value="" >select an option...</option>
						[#list researchAreas as researchArea]
						  	<option value="${researchArea}">${researchArea.name}</option>
						[/#list]
						  
						</select>

					</div>

					<!-- research program-->
					<div class="col-md-6 ">
						<!-- [@customForm.select name="capdev.researchProgram.id" listName="researchPrograms" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.form.researchProgram" placeholder="select option..."  /] -->
						[@s.label key="capdev.form.researchProgram" /]
						<select class="selectpicker" name="capdev.researchProgram" >
							<option value="" >select an option...</option>
						[#list researchPrograms as researchprogram]
						  	<option value="${researchprogram}">${researchprogram.name}</option>
						[/#list]
						</select>
					</div>
				</div>
			</div>
			<!-- project-->
			<div class="row newCapdevField">
				<div class="col-md-12 ">
					<!-- [@customForm.select name="capdev.project.id" listName="projects" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.project" placeholder="select option..."  /] -->
					[@s.label key="capdev.form.project" /]
					<select class="selectpicker" name="capdev.project" >
							<option value="" >select an option...</option>
						[#list projects as proj]
							[#if proj.name??]
							<option value="${proj}">${proj.name}</option>
							[#else]
							<option value="" hidden="true"></option>
							[/#if]
						[/#list]
					</select>
				</div>
			</div>
			<!-- CRP -->
			<div class="row">
				<div class="col-md-12 newCapdevField">
					<div class="col-md-6 ">
						<!-- [@customForm.select name="capdev.crp.id" listName="crps" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.crp" placeholder="select option..."  /] -->
						[@s.label key="capdev.form.crp" /]
						<select class="selectpicker" name="capdev.crp" >
							<option value="" >select an option...</option>
						[#list crps as crp]
							<option value="${crp}">${crp.name}</option>
						[/#list]
					</select>
					</div>
					<!-- num participants -->
					<div class="col-md-6 group individual">
						[@customForm.input name="capdev.numParticipants" i18nkey="capdev.form.numParticipants" type="text"  editable=true  /]
					</div>
				</div>
			</div>

			<!-- Disciplines-->
			<div class="row ">
				<div class="col-md-12 newCapdevField approachesListTitle">
					[@s.text name="capdev.form.listOfApproaches"][/@s.text] 
				</div>
			</div>
			<div class="row approachesListContainer">
				<div class="col-md-12 newCapdevField">
					[@customForm.select name="" listName="disciplines" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.form.selectApproach" className="disciplinesSelect" multiple=false placeholder="select option..."  /]
				</div>
				<div id="disciplinesList" class="col-md-12 newCapdevField approachesList">
					<ul class="list">
						[#if capdev.disciplines?has_content]
						[#list capdev.disciplines as discipline]
						<li id="" class="discipline clearfix col-md-3">
							[#if editable ]
							<div class="removeDiscipline removeIcon" title="Remove discipline"></div>
							[/#if]
							<input class="id" type="hidden" name="" value="" />
							<input class="disciplineId" type="hidden" name="disciplinesSelected[-1]" value="" />
							<span class="name"></span>
							<div class="clearfix"></div>
						</li>
						[/#list]
						[#else]
						<p class="emptyText"> [@s.text name="capdev.notDisciplines" /]</p> 
						[/#if]
					</ul>
				</div>
			</div>

			<!-- Targeted public-->
			<div class="row">
				<div class="col-md-12 newCapdevField approachesListTitle">
					[@s.text name="Target Groups"][/@s.text] 
				</div>
			</div>
			<div class="row borderContainer">
				<div class="col-md-12 newCapdevField ">
					[@customForm.select name="" listName="targetGroups" keyFieldName="id" displayFieldName="name"  i18nkey="Select a target group" className="targetGroupsSelect" multiple=false placeholder="select option..."  /]
				</div>

				<div id="targetGroupsList" class="col-md-12 newCapdevField ">
					<ul class="list">
						[#if capdev.TargetGroups?has_content]
						[#list capdev.TargetGroups as targetGroup]
						<li id="" class="targetGroup clearfix col-md-3">
							[#if editable ]
							<div class="removeTargetGroup removeIcon" title="Remove targetGroup"></div>
							[/#if]
							<input class="id" type="hidden" name="" value="" />
							<input class="tgId" type="hidden" name="targetGroupsSelected[-1]" value="" />
							<span class="name"></span>
							<div class="clearfix"></div>
						</li>
						[/#list]
						[#else]
						<p class="emptyText"> [@s.text name="capdev.notTargetGroups" /]</p> 
						[/#if]
					</ul>
				</div>
			</div>

			<!-- Partners-->
			<div class="row">
				<div class="col-md-12 newCapdevField approachesListTitle">
					[@s.text name="Partners"][/@s.text] 
				</div>
			</div>
			<div class="row borderContainer">
				<div class="col-md-12 newCapdevField ">
					[@customForm.select name="" listName="partners" keyFieldName="id" displayFieldName="name"  i18nkey="Select a partner" className="partners" multiple=false placeholder="select option..."  /]
				</div>

				<div class="col-md-12 newCapdevField ">
					[#if capdevPartners?has_content]
					[#list capdevPartners as partner]

					[/#list]
					[/#if]
					<p class="text-center inf" style="display:${(capdevPartners?has_content)?string('none','block')}">[@s.text name="capdev.notPartners" /]</p>
				</div>
			</div>

			<!-- OutPuts-->
			<div class="row">
				<div class="col-md-12 newCapdevField objectivesTitle">
					[@s.text name="capdev.form.objectives"][/@s.text]
				</div>
			</div>
			<div class="row outComesContainer">
				<div class="col-md-12 newCapdevField">
					[@customForm.select name="" listName="outcomes" i18nkey="capdev.form.selectOutcome" className="outComes" multiple=false placeholder="select option..."  /]
				</div>

				<div class="col-md-12 newCapdevField outComesList">

					[#if capDevOutcomes?has_content]
					[#list capDevOutcomes as outcome]
					[@outComeMacro outcome /]  
					[/#list]
					[/#if]

					<p class="text-center inf" style="display:${(capDevOutcomes?has_content)?string('none','block')}">[@s.text name="capdev.notObjectives" /]</p>
				</div>
			</div>


			<!-- groups participants-->
			<div class="row grupsParticipantsForm">
				<div class="col-md-12 newCapdevField">
					<div class="col-md-6  participantsTitle">
						[@s.text name="capdev.form.participants"][/@s.text]
					</div>
					<div class="col-md-6 ">
						<div class="pull-right">
							<button type="button" class="" aria-label="Left Align" >
								
								<a class="addButton" href="[@s.url action='${centerSession}/downloadFile' /] ">[@s.text name="Download Template" /]</a> 
							</button>
						</div>
					</div>
				</div>

				<div class="row newCapdevField">
					<div class="col-md-12 newCapdevField participantsBox">
						
							<!-- [@customForm.inputFile name="uploadFile" /] -->
							[@s.file id="uploadFile" name="uploadFile" label="Select a File to upload" size="40" class="uploadParticipants"/]

							<button type="button"  id="btnDisplay" class="" aria-label="Left Align" >
								 preview
							</button>
							<!-- <div class="col-md-6 ">
								<div class="pull-right">
									<button type="button" class="" aria-label="Left Align" >

										<a class="addButton" href="[@s.url action='${centerSession}/uploadFile' /] ">[@s.text name="Download Template" /]</a> 
									</button>
								</div>
							</div> -->
					</div>
					<div class="col-md-12">
						
						preview

						[#if previewListHeader?has_content]
							[#list previewListHeader as key]
								<p>	${key} </p>
							[/#list]
						[/#if]
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
					[@customForm.input name="participant.code" i18nkey="code" type="text" required=true /]
				</div>
				<div class="col-md-12">
					<div class="col-md-6">
						[@customForm.input name="participant.name" i18nkey="First name" type="text" required=true /]
					</div>
					<div class="col-md-6">
						[@customForm.input name="participant.lastName" i18nkey="Last name" type="text" required=true /]
					</div>
				</div>
				<div class="col-md-12">
					<div class="col-md-6">
						<div>
							[@s.label key="Gender" /]
						</div>
						<select class="selectpicker" name="participant.gender">
							<option value="1" >Male</option>
						  	<option value="2">Female</option>
						</select>
					</div>
					<div class="col-md-6">
						[@customForm.input name="participant.citizenship" i18nkey="citizenship" type="text" required=true /]
					</div>
				</div>
				<div class="col-md-12">
					<div class="col-md-6">
						[@customForm.input name="participant.countryOfResidence" i18nkey="Country of residence" type="text" required=true /]
					</div>
					<div class="col-md-6">
						[@customForm.input name="participant.highestDegree" i18nkey="Highest degree" type="text" required=true /]
					</div>
				</div>
				<div class="col-md-12">
					<div class="col-md-6">
						[@customForm.input name="participant.institution" i18nkey="Institution" type="text" required=true /]
					</div>
					<div class="col-md-6">
						[@customForm.input name="participant.email" i18nkey="Email" type="text" required=true /]
					</div>
				</div>
				<div class="col-md-12">
					<div class="col-md-6">
						[@customForm.input name="participant.supervisor" i18nkey="Supervisor" type="text" required=true /]
					</div>
					<div class="col-md-6">
						[@customForm.input name="participant.fellowship" i18nkey="Fellowship" type="text" /]
					</div>
				</div>
			</div>

			<!-- supporting documents -->
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
	

</div>




<!-- country list template-->
<ul style="display:none">
  <li id="capdevCountryTemplate" class="capdevCountry clearfix col-md-4">
      <div class="removeCountry removeIcon" title="Remove country"></div>
      <input class="id" type="hidden" name="" value="" />
      <input class="cId" type="hidden" name="capdevCountries[-1]" value="" />
      <span class="name"></span>
      <div class="clearfix"></div>
    </li>
</ul>

<!-- region list template-->
<ul style="display:none">
  <li id="capdevRegionTemplate" class="capdevRegion clearfix col-md-4">
      <div class="removeRegion removeIcon" title="Remove region"></div>
      <input class="id" type="hidden" name="" value="" />
      <input class="cId" type="hidden" name="capdevRegions[-1]" value="" />
      <span class="name"></span>
      <div class="clearfix"></div>
    </li>
</ul>

<!-- disciplines template-->
<ul style="display:none">
  <li id="disciplineTemplate" class="discipline clearfix col-md-4">
      <div class="removeDiscipline removeIcon" title="Remove discipline"></div>
      <input class="id" type="hidden" name="" value="" />
      <input class="disciplineId" type="hidden" name="disciplinesSelected[-1]" value="" />
      <span class="name"></span>
      <div class="clearfix"></div>
    </li>
</ul>

<!-- target group template-->
<ul style="display:none">
  <li id="targetGroupTemplate" class="targetGroup clearfix col-md-4">
      <div class="removetargetGroup removeIcon" title="Remove targetGroup"></div>
      <input class="id" type="hidden" name="" value="" />
      <input class="tgId" type="hidden" name="targetGroupsSelected[-1]" value="" />
      <span class="name"></span>
      <div class="clearfix"></div>
    </li>
</ul>


[#include "/WEB-INF/views/capDev/searchContactPerson.ftl" /]



[#include "/WEB-INF/global/pages/footer.ftl"]



[#macro objectiveMacro element index=0 isTemplate=false]
	
	<div id="objective-${isTemplate?string('template',(element.id)!)}" class="objective  borderBox row"  style="display:${isTemplate?string('none','block')}">
		<div class="removeObjective removeIcon" title="Remove objective"></div>
		<div class="col-md-12">
			 [@customForm.input name="objectiveBody" i18nkey="Objective # ${index + 1}" type="text" /]
		</div>

	</div>
	
[/#macro]


<!-- [#macro disciplineMacro element isTemplate=false]
	<div id="approach-${isTemplate?string('template',(element)!)}" class="approach  borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeDiscipline removeIcon" title="Remove approach"></div>
		<div class="col-md-4">
			 [@s.text name="element" /]
		</div>
	</div>
[/#macro] -->


[#macro outComeMacro element isTemplate=false]
	<div id="outcome-${isTemplate?string('template',(element)!)}" class="outcome  borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeOutCome removeIcon" title="Remove outcome"></div>
		<div class="col-md-4">
			 [@s.text name="element" /]
		</div>
	</div>
[/#macro]





<!-- [#macro countryMacro element name index isTemplate=false]
	[#local customName = "${name}[${index}]"]
	<div id="country-${isTemplate?string('template',(index)!)}" class="country  borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeCountry removeIcon" title="Remove country"></div>
		<div class="col-md-4">
			${name}
			 
		</div>
			}
	</div>
[/#macro]


[#macro regionMacro element isTemplate=false]
	<div id="region-${isTemplate?string('template',(element)!)}" class="region borderBox col-md-4 " style="display:${isTemplate?string('none','block')}" >
		<div class="removeRegion removeIcon" title="Remove region"></div>
		<div class="col-md-4">
			 [@s.text name="element" /]
		</div>
	</div>
[/#macro] -->



