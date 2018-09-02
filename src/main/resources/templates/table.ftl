<div id="contentwrapper">
	<div class="main_content">
		<#include "top.ftl">


		<div class="row-fluid">
			<div class="span12">
				<h3 class="heading">快来灌水</h3>


					<div class="alert alert-error">
						<a class="close" data-dismiss="alert">×</a>
						<strong>操作信息:


							欢迎
						<#if user??>
						${user.username}
						<#else>
                            游客
						</#if>


						</strong>

					</div>

 					<#if user??>
					<div class="btn-group sepH_b">
						<button data-toggle="dropdown" class="btn dropdown-toggle">
							行数 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">


							<li><a href="user?action=setpage&page=5">默认5行</a></li>
							<li><a href="user?action=setpage&page=10">每页10行</a></li>
							<li><a href="user?action=setpage&page=2">每页2行</a></li>
						</ul>
					</div>
					</#if>





				<table class="table table-bordered table-striped table_vam"
					id="dt_gal">
					<thead>
						<tr>

							<th class="table_checkbox">序号</th>
							<th style="width: 50px">发布人</th>
							<th style="width: 100px">主帖标题</th>
							<th style="width: 420px">主帖内容</th>
							<th style="width: 60px">发布日期</th>
							<th style="width: 60px">操作</th>
						</tr>
					</thead>
					<tbody>


						<#if PB??>

							<#list PB.data as p>
								<tr>

									<td>${p_index}</td>
									<td><a href="/upload/.jpg"

										title="" class="cbox_single thumbnail">

											<img src="user?action=pic&id=${p.user.userid}"
											alt="" style="height: 50px; width: 50px" />

									</a>



									</td>
									<td>


									<a
										href="">${p.title}</a>
									</td>
									<td>${p.content}</td>
									<td>${p.datetime}</td>
									<td>

										<#assign uid=999/>
										<#if user??>

											<#assign uid=user.userid/>
										</#if>

                                        <a href="#rshow" title="灌水" data-toggle="modal"
                                           id="myp" data-backdrop="static"
                                           onclick="rshow( ${p.id},${uid},${p.user.userid})">
                                            <i class="icon-eye-open"></i>

                                        </a>

											<!-- 是本人贴可以删除和修改 -->
										<#if user??>
											<#if (user.userid==p.user.userid)>
											<a
												href="article?action=del&id=${p.id}"
												title="删除本帖"><i class="icon-trash"></i></a>
											</#if>
										</#if>
									</td>

								</tr>
							</#list>
						</#if>

					</tbody>
				</table>



			</div>
		</div>

			<#include "page.ftl">


	</div>


</div>