@echo off  
echo "复制数据........."  
echo.  
del jar\*
xcopy ruoyi-admin\target\ruoyi-admin.jar jar\
xcopy ruoyi-common\target\ruoyi-common-3.8.3.jar jar\
xcopy ruoyi-framework\target\ruoyi-framework-3.8.3.jar jar\
xcopy ruoyi-generator\target\ruoyi-generator-3.8.3.jar jar\
xcopy ruoyi-quartz\target\ruoyi-quartz-3.8.3.jar jar\
xcopy ruoyi-system\target\ruoyi-system-3.8.3.jar jar\
xcopy ruoyi-xindian\target\ruoyi-xindian-3.8.3.jar jar\
