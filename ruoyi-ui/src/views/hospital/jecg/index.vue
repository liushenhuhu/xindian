<template>
  <div class="container">
    <iframe
      class="home"
      allowfullscreen="true"
      :src=src>
    </iframe>
  </div>
</template>
<script>
import {getUserInfo,getInfoId} from "@/api/patient_management/patient_management";
import {getToken} from "@/utils/auth";

let windowHeight;
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.3",
      TableHeight: 100,
      src: null
    };
  },
  created() {
    this.src = 'https://bigscreen.mindyard.cn/index?code='+this.$route.query.hospitalCode+'&token='+getToken()
    //动态计算表格高度
  },
  activated() {
    this.src = 'https://bigscreen.mindyard.cn/index?code='+this.$route.query.hospitalCode+'&token='+getToken()
    //动态计算表格高度

  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  width: 100%;
  height: calc(100vh - 83px);
}
</style>
