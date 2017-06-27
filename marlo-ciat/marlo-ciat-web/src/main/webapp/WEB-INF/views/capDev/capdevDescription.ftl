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
	<div class="col-md-3 capDevMenu">
		[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
	</div>
	
	
	
	
	
	<div class="col-md-9 ">
	
	<div class="col-md-12">
			 Capacity Development Description	
	</div>
	
	<div class="col-md-12 form-group newCapdevForm"> 

		[@s.form action=actionName method="POST" enctype="multipart/form-data" cssClass=""]
		

		<div  class="fullForm" >
			
			<!-- research Area-->
			<div class="row">
				<div class="col-md-12 newCapdevField">
					<div class="col-md-6 ">
						<!-- [@customForm.select name="capdev.researchArea.id" listName="researchAreas" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.researchArea" placeholder="capdev.select"  /] -->
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
						<!-- [@customForm.select name="capdev.researchProgram.id" listName="researchPrograms" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.form.researchProgram" placeholder="capdev.select"  /] -->
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
					<!-- [@customForm.select name="capdev.project.id" listName="projects" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.project" placeholder="capdev.select"  /] -->
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
						<!-- [@customForm.select name="capdev.crp.id" listName="crps" keyFieldName="id" displayFieldName="name" i18nkey="capdev.form.crp" placeholder="capdev.select"  /] -->
						[@s.label key="capdev.form.crp" /]
						<select class="selectpicker" name="capdev.crp" >
							<option value="" >select an option...</option>
						[#list crps as crp]
							<option value="${crp}">${crp.name}</option>
						[/#list]
					</select>
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
					[@customForm.select name="" listName="disciplines" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.form.selectApproach" className="disciplinesSelect" multiple=false placeholder="capdev.select"  /]
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
					[@s.text name="capdev.targetgroup"][/@s.text] 
				</div>
			</div>
			<div class="row borderContainer">
				<div class="col-md-12 newCapdevField ">
					[@customForm.select name="" listName="targetGroups" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.targetgroupselect" className="targetGroupsSelect" multiple=false placeholder="capdev.select"  /]
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
					[@s.text name="capdev.partnerts"][/@s.text] 
				</div>
			</div>
			<div class="row borderContainer">
				<div class="col-md-12 newCapdevField ">
					[@customForm.select name="" listName="partners" keyFieldName="id" displayFieldName="name"  i18nkey="capdev.partnertSelect" className="partners" multiple=false placeholder="capdev.select"  /]
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
					[@customForm.select name="" listName="outcomes" i18nkey="capdev.form.selectOutcome" className="outComes" multiple=false placeholder="capdev.select"  /]
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


			

			


			




			<!-- buttons -->
			<div class="col-md-12">
				<div class="col-md-6">
					<div class="pull-right">
						[@s.submit type="button" name="save" cssClass="button-save"]<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> [@s.text name="form.buttons.save" /] [/@s.submit]

					</div>
				</div>

				

			</div>

		</div>

	</div>
	
	[/@s.form]

	</div>
	

</div>






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






