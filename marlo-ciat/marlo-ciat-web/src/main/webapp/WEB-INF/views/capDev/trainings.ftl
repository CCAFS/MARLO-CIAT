[#ftl]

[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign customCSS = ["${baseUrl}/css/capDev/capacityDevelopment.css"] /]
[#assign customJS = ["${baseUrl}/js/capDev/capacityDevelopment.js"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


<script src="${baseUrl}/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${baseUrl}/js/capDev/capacityDevelopment.js"></script>

	<div class="container">
		<div class="row titleContainer">
			<div class="col-md-12">
				<p>Capacity Development</p>
			</div>
			
		
		</div>
		
		<div class="row conventionContainer">
			<div class="col-md-12 itemName">
				<div class="col-md-6"> Items:</div>
				<div class="col-md-6">Colors:</div>
			</div>
			<div class="col-md-12">
				<div class="col-md-6 items">
					<div class="row">
						<div class="col-md-4">
							<img src="${baseUrl}/images/global/participants.png" class="capDevIconConvention" />
							Lista of participants
						</div>
						
						<div class="col-md-4">
							<img src="${baseUrl}/images/global/deliverable.png" class="capDevIconConvention" />
							Deliverables
						</div>
						
						<div class="col-md-4">
							<img src="${baseUrl}/images/global/attachFiles.png" class="capDevIconConvention" />
							Attachments
						</div>
						

					</div>
					
				</div>
				<div class="col-md-6">
					<div class="row">

						<div class="col-md-1 paintedDot">&nbsp</div>
						<div class="col-md-5">Has the item</div>
						<div class="col-md-1 notPaintedDot">&nbsp</div>
						<div class="col-md-5">Does not has the item</div>
					</div>
					
				</div>
		
			</div>
		</div>
		<div class="row searchArea">
			<div class="col-md-12">
				<div class="pull-right">
	  				<img src="${baseUrl}/images/global/search.png" class="searchIcon" />
				</div>
				<div class="pull-right ">
						<input type="text" name="search" class="searchInput"><br>
				</div> 
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-bordered">
				  <tr>
				    <th style="width: 3%">ID</th>
				    <th style="width: 15%">Title</th> 
				    <th style="width: 7%">Type</th>
				    <th style="width: 7%">Start date</th>
				    <th style="width: 7%">End date</th>
				    <th style="width: 7%">CRP</th>
				    <th style="width: 7%">Items</th>
				  </tr>
				  
				  [#if capDevs?has_content]
				  [#list capDevs as i]
					  <tr>
					    <td>${i.id}</td>
					    <td><a  href="[@s.url action='${centerSession}/detailCapdev'][@s.param name='capDevID']${i.id?c}[/@s.param][/@s.url]">${i.title}</a></td> 
					    <td>${i.capdevType.name}</td>
					    <td>--</td>
					    <td>--</td>
					    <td>--</td>
					    <td>
						    		<div class=" iconContentBox">
						    			<img src="${baseUrl}/images/global/participants.png" class="capDevIcon" />
						    		</div>
						    		<div class=" iconContentBox">
						    			<img src="${baseUrl}/images/global/deliverable.png" class="capDevIcon" />
						    		</div>
						    		<div class=" iconContentBox">
						    			<img src="${baseUrl}/images/global/attachFiles.png" class="capDevIcon" />
						    		</div>
					    	
					    </td>
					  </tr>
				  [/#list]

				  [/#if]
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				
				<div class="buttons">
		        	<div class="buttons-content">        
			          	<a class="addButton" href="[@s.url action='${centerSession}/detailCapdev' ][@s.param name='capDevID']-1[/@s.param][/@s.url]">[@s.text name="Add capacity development" /]</a>

			          	
			        	<div class="clearfix"></div>
		        	</div>
    			</div>
			</div>
		</div>
	 </div>




	 <!-- <div id="dialog-capdevCategory" style="display: none" class="capdevCategoryModal">
	 	<form class="pure-form">
	 		<div class="row newCapdevField"> 
	 		<span class="glyphicon glyphicon-remove-circle close-dialog"></span>
	 			<div class="col-md-12 newCapdevField">
	 				[@s.text name="Category" ][/@s.text] 
	 			</div>
	 			<div class="col-md-12">
	 				<div class="col-md-3">
	 					<div class="radio">
	 						<label><input  id="individual" class="radioButton" type="radio"  name="capdevCategory" value="1">Individual</label>
	 					</div>
	 				</div>
	 				<div class="col-md-3">
	 					<div class="radio">
	 						<label><input id="groups" class="radioButton" type="radio" name="capdevCategory" value="2">Groups</label>
	 					</div>
	 				</div>



	 			</div>

	 			<div class="col-md-12">
	 				<div class="col-md-6">
				
				<div class="buttons">
		        	<div class="buttons-content">        
			          	 <a class="addButton" href="[@s.url action='${centerSession}/detailCapdev'][@s.param name='capDevID']-1[/@s.param] [/@s.url]">[@s.text name="Accept" /]</a> 

			          	 

			          	  
			        	<div class="clearfix"></div>
		        	</div>
    			</div>
			</div>
			<div class="col-md-6">
				
				<div class="buttons">
		        	<div class="buttons-content">        
			          	<div id="" class="addButton addCapDevButton" >[@s.text name="Cancel" /]</div>
			        	<div class="clearfix"></div>
		        	</div>
    			</div>
			</div>
	 			</div>
	 		</div>

	 	</form>
	 </div> -->






[#include "/WEB-INF/global/pages/footer.ftl"]