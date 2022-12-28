<template>
  <div class="divide">
    <span>账号id:</span>
    <el-input class="message" v-model="assignedAno.userId" placeholder="请输入账号"/>
    <br>
    <span>患者id:</span>
    <el-input class="message" type="textarea" :rows="2" v-model="assignedAno.patientId" placeholder="请输入患者id"></el-input>
    <br>
    <el-button class="sub" type="primary" @click=" submitMessage() ">确定</el-button>
  </div>
</template>
<script>
//调用接口
// import { saveSubject } from "@/api/content";
import {assigned_ano} from "@/api/alert_log/alert_log";

export default {
  name: "divide",
  data() {
    return {
      assignedAno: {
        userId: null,
        patientId: null
      }
    };
  },
  methods: {
    //添加增加题目
    submitMessage() {
      console.log(this.assignedAno)
      assigned_ano(this.assignedAno).then(res =>{
        if (res.msg === "success")
          alert("分配成功")
      })
    },
  },
};
</script>
<style scoped>
.divide {
  margin: 0 auto;
  width: 600px;
}

.message {
  width: 300px;
  margin-left: 30px;
  margin-top: 30px;
}

.sub {
  margin: 30px 260px;
}
</style>
