[#ftl]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


<div class="row">
<div class="col-md-3">
[#include "/WEB-INF/views/capDev/menu-capdev.ftl" /]
</div>


<div class="col-md-9">
	<p>Capacity Development</p>

[# --New capacity button--]
	[#list capDevs as i]
			<div class="col-md-12">
				<div class="col-md-6">
				<p>${i.id} - ${i.title}</p>
				</div>
				
				<div class="col-md-2">
					pariciapants
				</div>
				<div class="col-md-2">
					deliverables
				</div>
				<div class="col-md-2">
					attach files
				</div>
				
				
			</div>
			
	[/#list]
	
	
	</div>
</div>






[#include "/WEB-INF/global/pages/footer.ftl"]