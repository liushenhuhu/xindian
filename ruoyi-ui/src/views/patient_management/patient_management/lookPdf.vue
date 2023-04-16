<template>
    <iframe
      width="100%"
      :height="TableHeight"
      allowfullscreen="true"
      :src=src>
    </iframe>
</template>

<script>
import {getUserInfo} from "@/api/patient_management/patient_management";
import $ from "jquery";

export default {
  name: "lookPdf",
  data() {
    return {
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null
    };
  },
  created() {
    let pId=this.$route.params.pId
    let ecgTpye=this.$route.params.ecgTpye
    this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
    // this.TableHeight+=180;
    let _this=this;
    $.ajax({
      type: "get",
      url: "https://ecg.mindyard.cn:84/DECGReport/"+ecgTpye+"/report/"+pId+"/"+pId+".pdf",
      success: function () {
          console.log("succ")
          _this.src="https://ecg.mindyard.cn:84/DECGReport/"+ecgTpye+"/report/"+pId+"/"+pId+".pdf";

      },
      error: function () {
        alert("未生成报告，请先生成报告！")
    }
    })
    this.src=_this.src
  },
  activated(){
    if(this.$route.params.pId!=null){
      this.src=null
      let pId=this.$route.params.pId
      let ecgTpye=this.$route.params.ecgTpye
      this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
      // this.TableHeight+=180;
      let _this=this;
      $.ajax({
        type: "get",
        url: "https://ecg.mindyard.cn:84/DECGReport/"+ecgTpye+"/report/"+pId+"/"+pId+".pdf",
        success: function () {
          console.log("succ")
          _this.src="https://ecg.mindyard.cn:84/DECGReport/"+ecgTpye+"/report/"+pId+"/"+pId+".pdf";

        },
        error: function () {
          alert("未生成报告，请先生成报告！")
        }
      })
      this.src=_this.src
    }
  }
}
</script>

<style scoped>

</style>
