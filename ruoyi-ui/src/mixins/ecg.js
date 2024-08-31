/*
 * @Author: WHL
 * @Date: 2024-08-31 10:09:24
 * @LastEditors: WHL
 * @LastEditTime: 2024-08-31 10:11:00
 * @FilePath: /ruoyi-ui/src/api/mixins/ecg.js
 * @Description:
 */
import { updateAIReport } from "@/api/report/report";
export const mixins = {
  data() {
    return {
      aiResult: "",
    };
  },
  computed: {},
  created() {
    console.log("我是 mixin 中的 created 生命周期函数");
  },
  mounted() {},
  methods: {
    editAiResult() {
      console.log("失去了焦点");
      updateAIReport({
        pId: this.pId,
        intelligentDiagnosis: this.aiResult,
      }).then((res) => {
        if (res.code == 200) {
          this.$message.success("修改成功");
        }
      });
    },
  },
};
