[#ftl]

<div id="dialog-searchContactPerson" style="display:none">

	<form class="pure-form">
		<div class="dialog-content">
			<div class="col-md-12">
				<span class="glyphicon glyphicon-remove-circle close-dialog"></span>
				<br />
				<br />

			</div>	

			<div class="col-md-12">
				
			</div>

			<div class="accordion-block">

				<div class="searchcontact-content clearfix">
					<div class="searchcontact-input">
						[@customForm.input name="" showTitle=false type="text" i18nkey="" placeholder="First name, last name or email"/]
						<div class="search-loader" style="display:none"><img src="${baseUrl}/images/global/loading_2.gif"></div>
					</div>  
					<div class="search-button">[@s.text name="Search" /]</div>
				</div>

			<!--<div class="contactList panel secondary">
				<div class="panel-head"> [@s.text name="AD users List" /]</div>
				<div class="panel-body"> 
					<p class="userMessage">
						[@s.text name="No se encontro resultados"]
						[@s.param name="0"]<span class="link">[@s.text name="form.buttons.clickingHere" /]</span>[/@s.param]
						[/@s.text] 
					</p>
					<ul></ul>
				</div>

			</div> -->

		</div>	



		</div>

	</form>









	


	[#-- User Template --]
	<ul style="display:none"> 
		<li id="userTemplate">
			<span class="contact firstname">{firstName}</span>  
			<span class="contact lastname">{lastName}</span>  
			<span class="contact email">{email}</span>  
			<span class="listButton select">[@s.text name="form.buttons.select" /]</span>
		</li> 
	</ul> 
	


</div>