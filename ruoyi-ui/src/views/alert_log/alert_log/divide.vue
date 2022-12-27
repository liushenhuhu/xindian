<template>
  <div class="divide">
    <p>账号: {{ userId }}</p>
    <el-input class="message" v-model="assignedAno.userId" placeholder="请输入账号" />
    <br>
    <span>患者id:</span>
    <p style="white-space: pre-line;">{{ pId }}</p>

    <el-input class="message" type="textarea" :rows="2" v-model="assignedAno.pId" placeholder="请输入患者id"></el-input>
    <br>
    <el-button class="sub" type="primary" @click=" submitMessage() ">确定</el-button>
  </div>
</template>
<script>
//调用接口
// import { saveSubject } from "@/api/content";
import {assigned_ano} from "@/api/alert_log/alert_log";

export default {
  name:"divide",
  data() {
    return {
      assignedAno:{
        userId: "",
        pId:"",
      }
    };
  },
  methods: {
    //添加增加题目
    submitMessage() {
      console.log(this.assignedAno.userId)
      console.log(this.assignedAno.pId);
      console.log(this.assignedAno)
      assigned_ano()
    },
  },
};
</script>
<style scoped>
.divide{
  margin: 0 auto;
  width: 600px;
}
.message{
  width: 300px;
  margin-left: 30px;
}
.sub{
  margin:30px 260px;
}
</style>
