[#ftl]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]


[#list capDevs as i]
	<p>${i.id} - ${i.title}</p>
[/#list]

[#include "/WEB-INF/global/pages/footer.ftl"]