<template>
  <div>
    <iframe
      width="100%"
      :height="TableHeight"
      allowfullscreen="true"
      :src=src  ref="myFrame">
    </iframe>
  </div>

</template>

<script>
import $ from "jquery";
import {getPdf, getSX_PDFByPId} from "@/api/patient_management/patient_management";

export default {
  name: "lookPdf",
  data() {
    return {
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null,
      flagCre:0,
      num:1,
      loading:null
    };
  },
  created() {
    this.loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    let pId=this.$route.query.pId
    this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
    this.getPdf(pId)
    const iframe = this.$refs.myFrame;
    iframe.contentWindow.scrollTo(0, 0);
  },
  activated(){
    this.loading = this.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });
    this.num +=1
    if(this.$route.query.pId!=null&&this.num>2){
      window.scrollTo(0, 0)
      this.src=null
      let pId=this.$route.query.pId
      this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
      this.getPdf(pId)

      const iframe = this.$refs.myFrame;
      iframe.contentWindow.scrollTo(0, 0);
    }
  },

  methods: {
    getPdf(pId){
      let _this=this;
      $.ajax({
        type: "get",
        url: "https://ecg.mindyard.cn:84/uploadPath/pdf/"+pId+".pdf",
        success: function () {
          _this.src="https://ecg.mindyard.cn:84/uploadPath/pdf/"+pId+".pdf";
          _this.loading.close();
        },
        error: function () {
          _this.getJEcgPdf()
          this.$message({
            type: 'info',
            message: '正在生成报告，请稍等'
          });
        }
      })
      this.src=_this.src
    },
    getJEcgPdf(){
      let _this=this;
      let obj = {
        pId:this.$route.query.pId
      }
      getPdf(obj).then(res=>{
        this.src = res.msg
        this.loading.close();
      }).catch(err=>{
        this.loading.close();
      })
    }
  }
}
</script>

<style scoped>

</style>
