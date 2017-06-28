[#ftl]

<div id="dialog-searchContactPerson" style="display:none">

	<form class="pure-form">
		<div class="dialog-content">
			<div class="col-md-12">
				<span class="glyphicon glyphicon-remove-circle close-dialog"></span>
				<br />
				<br />

			</div>	

			
			<div class="col-md-12 searchFields">
				<div class="searchcontact-content clearfix">
					<div class="col-md- searchcontact-input">
						[@customForm.input name="" showTitle=false type="text"  className='contact' placeholder="capdev.adUsersplaceHolder"/]
						<div class="col-md- search-loader" style="display:none;"><img src="${baseUrl}/images/global/loading_2.gif"></div>
					</div>  
					<div class="col-md- search-button">[@s.text name="capdev.search" /]</div>
				</div>
				
			</div>
			

			<div class="col-md-12">
				<div class="contactList panel secondary">
					<div class="panel-head"> [@s.text name="capdev.adUsersList" /]</div>
					<div class="panel-body"> 
						<p class="userMessage">
							no se encontraron resultados para la entrada ingresada
							
							 
						</p>
						<ul></ul>
					</div>
				</div> 
			</div>

		</div>

	</form>

	


	[#-- User Template --]
	<ul style="display:none"> 
		<li id="userTemplate">
			  
			<span class="idUser">{idUser}</span>
			<span class="contact firstname">{firstName}</span>  
			<span class="contact lastname">{lastName}</span>  
			<span class="contact email">  {email} </span>  
			<span class="listButton select">[@s.text name="form.buttons.select" /]</span>
		</li> 
	</ul> 
	


</div>