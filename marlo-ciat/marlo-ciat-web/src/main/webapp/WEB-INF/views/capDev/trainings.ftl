[#ftl]

[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign customCSS = ["${baseUrl}/css/capDev/capacityDevelopment.css"] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


<div class="row">
<div class="col-md-3">
[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
</div>


<div class="col-md-9">
	<p>Capacity Development</p>
	
	<button type="button" class="addButton">[@s.text name="Add capacity development" /]</button>
	[#list capDevs as i]
			<div class="col-md-12">
				<div class="col-md-6 trainingContentBox">
				<p>${i.id} - ${i.title}</p>
				</div>
				
				<div class="col-md-2 iconContentBox">
					<img src="${baseUrl}/images/global/participants.png" class="capDevIcon" />
				</div>
				<div class="col-md-2 iconContentBox">
					<img src="${baseUrl}/images/global/deliverable.png" class="capDevIcon" />
				</div>
				<div class="col-md-2 iconContentBox">
					<img src="${baseUrl}/images/global/attachFiles.png" class="capDevIcon" />
				</div>
				
				
			</div>
			
	[/#list]
	
	
	</div>
</div>






[#include "/WEB-INF/global/pages/footer.ftl"]