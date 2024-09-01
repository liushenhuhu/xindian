/*
 * @Author: WHL
 * @Date: 2024-08-15 16:42:36
 * @LastEditors: WHL
 * @LastEditTime: 2024-08-31 09:58:07
 * @FilePath: /ruoyi-ui/src/api/report/report.js
 * @Description:
 */
import request from "@/utils/request";

// 查询报告列表
export function listReport(query) {
  return request({
    url: "/report/report/list",
    method: "get",
    params: query,
  });
}
export function getWebList(query) {
  return request({
    url: "/report/report/web/list",
    method: "get",
    params: query,
  });
}
export function getWeekReportList(query) {
  return request({
    url: "/weekReport/report/list",
    method: "get",
    params: query,
  });
}

// 查询报告详细
export function getReport(reportId) {
  return request({
    url: "/report/report/" + reportId,
    method: "get",
  });
}

// 查询报告详细
export function getReportByPId(pId) {
  return request({
    url: "/report/report/getInfoByPid/" + pId,
    method: "get",
  });
}

// 新增报告
export function addReport(data) {
  return request({
    url: "/report/report",
    method: "post",
    data: data,
  });
}

// 修改报告
export function updateReport(data) {
  return request({
    url: "/report/report",
    method: "put",
    data: data,
  });
}
// 修改AI报告
export function updateAIReport(data) {
  return request({
    url: "/report/report/putReport ",
    method: "put",
    data: data,
  });
}
// 删除报告
export function delReport(reportId) {
  return request({
    url: "/report/report/" + reportId,
    method: "delete",
  });
}

export function getCommonTerms() {
  return request({
    url: "/report/report/getCommonTerms",
    method: "get",
  });
}
export function reportEarlyWarningMsg(data) {
  return request({
    url: "/report/report/reportEarlyWarningMsg",
    method: "get",
    params: data,
  });
}
