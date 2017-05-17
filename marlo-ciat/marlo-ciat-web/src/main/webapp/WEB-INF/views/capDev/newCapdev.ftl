[#ftl]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign customCSS = ["${baseUrl}/css/capDev/capacityDevelopment.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]



<div class="container"> 
	<div class="col-md-3 capDevMenu">
		[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
	</div>
	
	
	
	[@s.form action="add" method="POST" enctype="multipart/form-data" cssClass=""]
	
	<div class="col-md-9 ">
	
	<div class="col-md-12">
			New Capacity Development		
	</div>
	
	<div class="col-md-12 form-group newCapdevForm"> 

		<!-- Title-->
		<div class="row">
			<div class="col-md-12 "> 
				<div class="newCapdevField"> 
				[@customForm.input name="capdev.title" type="text" i18nkey="capdev.form.title"  required=true  /]
				</div>
			</div>
		</div>
		<!-- type-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 "> 
				[@customForm.select name="capdev.title"listName="" i18nkey="capdev.form.type" placeholder="select option..." required=true /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="capdev.title" i18nkey="capdev.form.contactPerson" type="text"   required=true  /]
			</div>
			</div>
		</div>
		<!-- Strart date-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.input name="capdev.title" i18nkey="capdev.form.startDate" type="text"   required=true  /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="capdev.title" i18nkey="capdev.form.endDate" type="text"   required=true  /]
			</div>
			</div>
		</div>
		<!-- Country-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.country" placeholder="select option..." required=true /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="capdev.title" i18nkey="capdev.form.region" type="text"   required=true  /]
			</div>
			</div>
		</div>
		<!-- research Area-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.researchArea" placeholder="select option..." required=true /]
			</div>
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.researchProgram" placeholder="select option..." required=true /]
			</div>
			</div>
		</div>
		<!-- project-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
				[@customForm.select name="" listName="" i18nkey="capdev.form.project" placeholder="select option..." required=true /]
			</div>
		</div>
		<!-- CRP and Dictated by-->
		<div class="row">
			<div class="col-md-12 newCapdevField">
			<div class="col-md-6 ">
				[@customForm.select name="" listName="" i18nkey="capdev.form.crp" placeholder="select option..." required=true /]
			</div>
			<div class="col-md-6 ">
				[@customForm.input name="capdev.title" i18nkey="capdev.form.dictated" type="text"   required=true  /]
			</div>
			</div>
		</div>
		<!-- Approaches-->
		<div class="row ">
			<div class="col-md-12 newCapdevField approachesListTitle">
				[@s.text name="capdev.form.listOfApproaches"][/@s.text] 
			</div>
		</div>
		<div class="row approachesListContainer">
			<div class="col-md-12 newCapdevField ">
				
			</div>
			
			<div class="col-md-12 newCapdevField">
				[@customForm.select name="" listName="" i18nkey="capdev.form.selectApproach" placeholder="select option..."  /]
			</div>
		</div>
		<!-- Objectives-->
		<div class="row">
			<div class="col-md-12 newCapdevField objectivesTitle">
				[@s.text name="capdev.form.objectives"][/@s.text]
			</div>
		</div>
		
		<div class="row objectivesContainer">
			<div class="col-md-12 newCapdevField objectivesBox">
				aqui van los objetivos insertados
			</div>
			<div class="col-md-12 newCapdevField addObjectiveButton">
				<div class="pull-right">
					<button type="button" class="" aria-label="Left Align" >
	  				Add objective
					</button>
				</div>
			</div>
		</div>
		<!-- participants-->
		<div class="row">
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
		</div>

		<div class="row">
			<div class="col-md-12 newCapdevField participantsBox">
				[@customForm.inputFile name="" /]
			</div>
		</div>

		
		
		</div>

	</div>
	
	[/@s.form]
	

</div>

