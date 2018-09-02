
<div class="pagination">
    <#if PB??>

    <ul>
        <li>

                <a href="article?action=quarryAll&page=1">首页</a>
			
        </li>
        <li>

			       <#if (PB.curPage>1)>
					<a href="article?action=quarryAll&page=${PB.curPage-1}">前一页</a>
                   </#if>

		</li>
    	<li>
                <#if (PB.maxPage=0)>
                    <a>1</a>
                    <#else >
                <#list 1..PB.maxPage as i>
				<a href="article?action=quarryAll&&page=${i}">${i}</a>

			</#list>
                </#if>
        </li>

    	<li>
			  <#if (PB.curPage<PB.maxPage)>
				<a href="article?action=queryAll&&page=${PB.curPage+1}">下一页</a>
              </#if>
        </li>

    	<li>

			
                <a href="article?action=quarryAll&&page=${PB.maxPage}">尾页</a>
			

        </li>

    </ul>
    </#if>
</div>