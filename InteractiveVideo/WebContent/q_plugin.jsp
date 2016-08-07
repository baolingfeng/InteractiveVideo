<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">1. 视频中activePage.openEditor第一个参数类型是
	<input class="underline-input w3-light-grey " name="q1">? <br/>
	第二个String参数代表： 
	<label class="w3-large"><input name="q1" id="1" value="1" type="radio"> editor id</label>
	<label class="w3-large"><input name="q1" id="2" value="2" type="radio"> editor class</label>
	<span name="q1_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="1" size="25" type="hidden">
	<input name="q1_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">2. 按视频填空： <span name="q2_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span><br/>
	<span style="padding-left:20px">public void doSaveAs() {</span> <br/>
	<span style="padding-left:40px">FileDialog fd = new FileDialog(<input class="underline-input w3-light-grey " name="q2" size="50"/>)</span>;
	<br/>
	<span style="padding-left:40px">......</span>
	</p>
	<input name="qnumber" value="2" size="25" type="hidden">
	<input name="q2_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">3. 根据视频，请选择下列几个空格应该填的内容:
	<span name="q3_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span> <br/>
	<span style="padding-left:20px">text.addKeyListener(new KeyListener(){</span> <br/>
	<span style="padding-left:60px">@Override</span> <br/>
	<span style="padding-left:60px">public void ___________(KeyEvent event){</span> <br/>
	<span style="padding-left:100px">if(event.stateMask == SWT.CTRL && event.keyCode == 's'){</span> <br/>
	<span style="padding-left:140px">___________;</span> <br/>
	<span style="padding-left:100px">}</span> <br/>
	<span style="padding-left:60px">}</span> <br/>
	<br/>
	<span style="padding-left:60px">@Override</span> <br/>
	<span style="padding-left:60px">public void ____________(KeyEvent event){</span> <br/>
	<br/>
	<span style="padding-left:60px">}</span> <br/>
	<span style="padding-left:20px">});</span> <br/>
	</p>
	<input name="qnumber" value="3" size="25" type="hidden">
	<input name="q3_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q3" id="1" value="1" type="checkbox"> keyPressed, doSave(), keyReleased</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="2" value="2" type="checkbox"> keyPressed, doSave(null), keyReleased</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="3" value="3" type="checkbox"> keyPressed, doSave(new NullProgressMonitor()), keyReleased</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="4" value="4" type="checkbox"> keyReleased, doSave(), keyPressed</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="5" value="5" type="checkbox"> keyReleased, doSave(null), keyPressed</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="6" value="6" type="checkbox"> keyReleased, doSave(new NullProgressMonitor()), keyPressed</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">4. 视频中某一个运行编写的Eclipse编辑器插件时，出现了错误，通过修改哪一条语句修复了这个bug？ 
	<span name="q4_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span><br/>
	原语句: <input class="underline-input w3-light-grey " name="q4" size="67"> <br/>
	修复的语句：<input class="underline-input w3-light-grey " name="q4" size="60"> <br/>
	</p>
	<input name="qnumber" value="4" size="25" type="hidden">
	<input name="q4_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">5. 视频中，建立一个继承EditorPart的类MyEditor有多少默认接口（不算构造函数）：
	<span name="q5_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="5" size="25" type="hidden">
	<input name="q5_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q5" id="1" value="1" type="checkbox"> 5个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q5" id="2" value="2" type="checkbox"> 6个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q5" id="3" value="3" type="checkbox"> 7个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q5" id="4" value="4" type="checkbox"> 8个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q5" id="5" value="5" type="checkbox"> 9个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q5" id="6" value="6" type="checkbox"> 10个</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">6. 视频中，类MyEditor中的默认接口被修改的类有几个:
		<span name="q6_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="6" size="25" type="hidden">
	<input name="q6_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q6" id="1" value="1" type="checkbox"> 3个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="2" value="2" type="checkbox"> 4个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="3" value="3" type="checkbox"> 5个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="4" value="4" type="checkbox"> 6个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="5" value="5" type="checkbox"> 7个</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="6" value="6" type="checkbox"> 8个</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">
		7. 下列哪些方法是视频中MyEditor和MyView都有的？: 
	<span name="q7_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span><br/>
	</p>
	<input name="qnumber" value="7" size="25" type="hidden">
	<input name="q7_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q7" id="1" value="1" type="checkbox"> createPartControl</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="2" value="2" type="checkbox"> setFocus</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="3" value="3" type="checkbox"> init</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="4" value="4" type="checkbox"> setText</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="4" value="4" type="checkbox"> doSave</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="4" value="4" type="checkbox"> doSaveAs</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q7" id="4" value="4" type="checkbox"> isDirty</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">8. 按视频中MyEditor类填空:<br/>
	<span style="padding-left:20px">public void createPartControl(<input class="underline-input w3-light-grey " name="q8">, arg0) {</span> <br/>
	<span style="padding-left:60px">Composite topComp = new Composite(<input class="underline-input w3-light-grey " name="q8">, <input class="underline-input w3-light-grey " name="q8">);</span> <br/>
	<span style="padding-left:60px">topComp.setLayout(<input class="underline-input w3-light-grey " name="q8">);</span> <br/>
	<br/>
	<span style="padding-left:60px">text = new Text(<input class="underline-input w3-light-grey " name="q8">, <input class="underline-input w3-light-grey " name="q8">);</span> <br/>
	<span style="padding-left:60px">......</span> <br/>
	<span name="q8_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="8" size="25" type="hidden">
	<input name="q8_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">
		9. 视频中Text类text获取长度的方法: 
	<span name="q9_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span><br/>
	</p>
	<input name="qnumber" value="9" size="25" type="hidden">
	<input name="q9_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q9" id="1" value="1" type="checkbox"> text.size()</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q9" id="2" value="2" type="checkbox"> text.length</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q9" id="3" value="3" type="checkbox"> text.length()</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q9" id="4" value="4" type="checkbox"> text.getText().size()</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q9" id="4" value="4" type="checkbox"> text.getText().length</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q9" id="4" value="4" type="checkbox"> text.getText().length()</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">10. 视频中，添加一个view，需要配置plugin.xml，请按视频填空:<br/>
	<span style="padding-left:20px">&lt;extension</span> <br/>
	<span style="padding-left:60px">point="<input class="underline-input w3-light-grey " name="q10" size="40">"&gt;</span> <br/>
	<span style="padding-left:60px">&lt;<input class="underline-input w3-light-grey " name="q10" /></span> <br/>
	<span style="padding-left:80px">name="WorkListCategory"</span> <br/>
	<span style="padding-left:80px">id="WorkListCategory"></span> <br/>
	<span style="padding-left:60px">&lt;/<input class="underline-input w3-light-grey " name="q10" />&gt;</span> <br/>
	<span style="padding-left:60px">&lt;view</span> <br/>
	<span style="padding-left:80px"><input class="underline-input w3-light-grey " name="q10" />="icons/sample.gif"</span> <br/>
	<span style="padding-left:80px"><input class="underline-input w3-light-grey " name="q10" />="test.plugin.part.MyView"</span> <br/>
	<span style="padding-left:80px"><input class="underline-input w3-light-grey " name="q10" />="WorkListCategory"</span> <br/>
	<span style="padding-left:80px"><input class="underline-input w3-light-grey " name="q10" />="WorkList"</span> <br/>
	<span style="padding-left:80px"><input class="underline-input w3-light-grey " name="q10" />="test.plugin.part.MyView"&gt;</span> <br/>
	<span style="padding-left:60px">&lt;/view&gt;</span> <br/>
	<span style="padding-left:20px">&lt;/extension&gt;</span> <br/>
	<span name="q10_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="8" size="25" type="hidden">
	<input name="q10_time" value="" size="25" type="hidden">
</div>