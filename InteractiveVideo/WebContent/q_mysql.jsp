<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">1. 按视频程序填空：<br/>
	<span name="q1_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	<span style="padding-left:20px">............ </span><br/>
    <span style="padding-left:20px"><input class="underline-input w3-light-grey " name="q1" size="60" placeholder="请在此输入"></input></span>
    <br/><br/>
    <span style="padding-left:20px">conn = DriverManager.getConnection(url, username, password);</span><br/>
    <span style="padding-left:20px">............</span>
	</p>
	<input name="qnumber" value="1" size="25" type="hidden">
	<input name="q1_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">2. 视频中在DBImpl类的方法executeStatement里是如何创建Statment类的?
	<span name="q2_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="2" size="25" type="hidden">
	<input name="q2_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q2" id="1" value="1" type="radio"> Statement stmt = new Statement();</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q2" id="2" value="2" type="radio"> Statement stmt = new Statement(sql);</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q2" id="3" value="3" type="radio"> Statement stmt = conn.createStatement();</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q2" id="4" value="4" type="radio"> Statement stmt = conn.createStatement(sql);</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">3. 如何遍历query的返回结果类Resultset rs:
	<span name="q3_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="3" size="25" type="hidden">
	<input name="q3_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q3" id="1" value="1" type="radio"> while(rs.hasNext()) {...}</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="2" value="2" type="radio"> while(rs.next()) {...}</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="3" value="3" type="radio"> while(rs != null) {rs.getNext();}</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q3" id="4" value="4" type="radio"> while(rs != null) {rs.next();}</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">4. 视频中，运行程序时，executePreparedStatement这个函数中query返回的结果包括:
	<span name="q4_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="4" size="25" type="hidden">
	<input name="q4_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q4" id="1" value="1" type="checkbox"> bookid: 4 authorid: 2 title: Cousin Bette </label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q4" id="2" value="2" type="checkbox"> bookid: 4 authorid: 5 title: Cousin Bette</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q4" id="3" value="3" type="checkbox"> bookid: 4 authorid: 5 title: In Cold Blood</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q4" id="4" value="4" type="checkbox"> bookid: 8 authorid: 2 title: Cousin Bette</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q4" id="5" value="5" type="checkbox"> bookid: 8 authorid: 5 title: Cousin Bette</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q4" id="6" value="6" type="checkbox"> bookid: 8 authorid: 5 title: In Cold Blood</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">5. Statement类下列方法execute、executeUpdate、executeQuery的返回值分别是
	<input class="underline-input w3-light-grey " name="q5" size="60" placeholder="请在此输入"></input>
	<span name="q5_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
	</p>
	<input name="qnumber" value="5" size="25" type="hidden">
	<input name="q5_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">6. 根据视频，如何获取插入后自增字段的值：
		<span name="q6_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="6" size="25" type="hidden">
	<input name="q6_time" value="" size="25" type="hidden">
	<div class="radio">
		<label class="w3-large"><input name="q6" id="1" value="1" type="radio"> int autoid = stmt.executeUpdate(sql); </label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="2" value="2" type="radio"> Resultset rs = stmt.executeUpdate(sql); int autoid = rs.getInt(1);</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="3" value="3" type="radio"> Resultset rs = stmt.executeUpdate(sql); int autoid = rs.getGeneratedKeys();</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="4" value="4" type="radio"> Resultset rs = stmt.executeQuery(sql); int autoid = rs.getGeneratedKeys();</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="5" value="5" type="radio"> stmt.executeUpdate(sql); Resultset rs = stmt.getGeneratedKeys(); int autoid = rs.getInt(1);</label>
	</div>
	<div class="radio">
		<label class="w3-large"><input name="q6" id="6" value="6" type="radio"> stmt.executeQuery(sql); Resultset rs = stmt.getGeneratedKeys(); int autoid = rs.getInt(1);</label>
	</div>
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">
		7. 视频中，运行程序时，main函数调用insertWithAutoIncrement这个函数，共有
	<input class="underline-input w3-light-grey " name="q7" placeholder="请在此输入"/>次有输出结果， 结果分别是
	<input class="underline-input w3-light-grey " name="q7" placeholder="请在此输入"/>。
	<span name="q7_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span><br/>
	</p>
	<input name="qnumber" value="7" size="25" type="hidden">
	<input name="q7_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">8. 视频中调用的存储过程的输出变量是
	<input class="underline-input w3-light-grey " name="q8" placeholder="请在此输入"/>，运行结果:
	<input class="underline-input w3-light-grey " name="q8" placeholder="请在此输入"/>
	<span name="q8_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="8" size="25" type="hidden">
	<input name="q8_time" value="" size="25" type="hidden">
</div>

<div class="qdiv w3-padding-jumbo disable-text">
	<p class="w3-large" style="margin-bottom:20px;">9. 在创建Statement时，可以将返回的ResultSet设置为scrollable and insensitive to updates，请根据如上要求完成填空：<br/>
	Statement stmt = con.createStatement(<input class="underline-input w3-light-grey " name="q9" placeholder="请在此输入"/>, <input class="underline-input w3-light-grey " name="q9" placeholder="请在此输入"/>);<br/> 
	ResultSet rs = stmt.executeQuery("SELECT a, b FROM TABLE2"
	<span name="q9_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span></p>
	<input name="qnumber" value="8" size="25" type="hidden">
	<input name="q9_time" value="" size="25" type="hidden">
</div>