<template>
  <div>

    <iframe
      width="100%"
      :height="TableHeight"
      allowfullscreen="true"
      :src=src>
    </iframe>
  </div>
</template>
<script>
import {getUserInfo,getInfoId} from "@/api/patient_management/patient_management";

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
      this.src = 'http://localhost:83/large_screen?hospitalId=1&token=eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjA3M2M5NmY4LTZmOGEtNDZjMy1hNTZiLWNhMjBiMGUwZDlkYyJ9.ig4XNvONpr4sNq1ZhaJKb2HQ86KQqfqj0jzKbiCTiTDkrnCjSdTU5DaXuECMtFJUCvTxJ0I-Ia-sebcJ5iqi6g'
      //动态计算表格高度
      let windowHeight = document.documentElement.clientHeight || document.bodyclientHeight;
      this.TableHeight = windowHeight + 180;
  },
  activated() {
      this.src = 'http://localhost:83/large_screen?hospitalId=1&token=eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjA3M2M5NmY4LTZmOGEtNDZjMy1hNTZiLWNhMjBiMGUwZDlkYyJ9.ig4XNvONpr4sNq1ZhaJKb2HQ86KQqfqj0jzKbiCTiTDkrnCjSdTU5DaXuECMtFJUCvTxJ0I-Ia-sebcJ5iqi6g'
      console.log(this.src)
      //动态计算表格高度
      let windowHeight = document.documentElement.clientHeight || document.bodyclientHeight;
      this.TableHeight = windowHeight + 180;
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
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
