<!--
 * @Author: h723999742 723999742@qq.com
 * @Date: 2024-05-25 10:09:27
 * @LastEditors: h723999742 723999742@qq.com
 * @LastEditTime: 2024-07-22 10:16:53
 * @FilePath: \ruoyi-ui\src\views\patient_management\patient_management\lookPdf.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <iframe width="100%" :height="TableHeight" allowfullscreen="true" :src=src ref="myFrame">
  </iframe>
</template>

<script>
import $ from "jquery";
import { getSX_PDFByPId } from "@/api/patient_management/patient_management";

export default {
  name: "lookPdf",
  data() {
    return {
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null,
      flagCre: 0,
      num: 1,
    };
  },
  created() {
    let pId = this.$route.query.pId
    let ecgType = this.$route.query.ecgType
    this.TableHeight = document.documentElement.clientHeight || document.bodyclientHeight;
    if (this.$route.query.sxStatus && this.$route.query.sxStatus == 1) {
      getSX_PDFByPId(pId).then(res => {
        if (res.msg) {
          this.src = res.msg
        } else {
          this.getPdf(ecgType, pId)
        }
      })
    } else {
      this.getPdf(ecgType, pId)
    }
    const iframe = this.$refs.myFrame;
    iframe.contentWindow.scrollTo(0, 0);
  },
  activated() {
    this.num += 1
    if (this.$route.query.pId != null && this.num > 2) {
      window.scrollTo(0, 0)
      this.src = null
      let pId = this.$route.query.pId
      let ecgType = this.$route.query.ecgType
      this.TableHeight = document.documentElement.clientHeight || document.bodyclientHeight;
      if (this.$route.query.sxStatus && this.$route.query.sxStatus == 1) {
        getSX_PDFByPId(pId).then(res => {
          if (res.msg) {
            this.src = res.msg
          } else {
            this.getPdf(ecgType, pId)
          }
        })
      }
      else {
        this.getPdf(ecgType, pId)
      }
      const iframe = this.$refs.myFrame;
      iframe.contentWindow.scrollTo(0, 0);
    }
  },

  methods: {
    getPdf(ecgType, pId) {
      let _this = this;
      if (ecgType == 'DECG_single') {
        console.log('ecgType', ecgType)
        $.ajax({
          type: "get",
          url: `https://ecg.mindyard.cn/data/pdf/${pId}.pdf`,
          success: function () {
            _this.src = `https://ecg.mindyard.cn/data/pdf/${pId}.pdf`;
          },
          error: function () {
            alert("未生成报告，请先生成报告！")
          }
        })
        return false
      }else{
        $.ajax({
          type: "get",
          url: "https://ecg.mindyard.cn:84/DECGReport/" + ecgType + "/report/" + pId + "/" + pId + ".pdf",
          success: function () {
            _this.src = "https://ecg.mindyard.cn:84/DECGReport/" + ecgType + "/report/" + pId + "/" + pId + ".pdf";
          },
          error: function () {
            alert("未生成报告，请先生成报告！")
          }
        })
      }
   
      this.src = _this.src
    }
  }
}
</script>

<style scoped></style>
