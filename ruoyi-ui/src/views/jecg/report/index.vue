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
  props: {
    dataId:null,
  },
  data() {
    return {
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null,
      flagCre:0,
      num:1,
      loading:null,
    };
  },
  created() {
    this.loading = this.$loading({
      lock: true,
      text: '正在生成报告，请稍等',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
      customClass: 'custom-loading'  // 添加自定义类名
    });
    console.log("created:")
    let pId= this.$route.query.pId ||this.dataId
    this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
    this.getPdf(pId)
    const iframe = this.$refs.myFrame;
    iframe.contentWindow.scrollTo(0, 0);
  },
  activated(){
    this.loading = this.$loading({
      lock: true,
      text: '正在生成报告，请稍等',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)',
      customClass: 'custom-loading'  // 添加自定义类名
    });
    console.log("activated")
    this.num +=1
    if(this.$route.query.pId!=null&&this.num>2){
      window.scrollTo(0, 0)
      this.src=null
      let pId= this.$route.query.pId ||this.dataId
      this.TableHeight=document.documentElement.clientHeight || document.bodyclientHeight;
      this.getPdf(pId)
      const iframe = this.$refs.myFrame;
      iframe.contentWindow.scrollTo(0, 0);
    }
  },

  methods: {
    getPdf(pId){
      this.getJEcgPdf()
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
