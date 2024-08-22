layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 监听
     */
    form.on('submit(addOrUpdateCusDevPlan)',function (data) {
        console.log(data.field)
        var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
        var url = ctx+"/cus_dev_plan/save";
        if($("input[name='id']").val()){
            url=ctx+"/cus_dev_plan/update";
        }

        $.post(url,data.field,function (res) {
            if(res.code==200){
                top.layer.msg(res.msg);
                top.layer.close(index);
                layer.closeAll("iframe");
                // 刷新父页面
                parent.location.reload();
            }else{
                layer.msg("操作失败");
            }
        });
        return false;
    });

    $("#closeBtn").click(function (){
        //先得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);
        //再执行关闭
        parent.layer.close(index)
    });
});