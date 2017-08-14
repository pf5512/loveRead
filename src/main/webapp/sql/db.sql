--  视图
create view moments_view
as
select m.id moments_id,m.user_id,u.nick_name user_name,u.head_img_url user_icon,m.moments_content,m.moments_img,m.book_id,m.book_name,m.crt_ts,m.moments_st,
rp.id reply_id,rp.replyer_id,rp.replyer_name,rp.reply_content,rp.parent_replyer_id,rp.parent_replyer_name
from tbl_moments m
left join tbl_wx_user_info u on m.user_Id = u.open_id
left join tbl_moments_reply rp on m.id = rp.moments_id
order by m.crt_ts desc,rp.reply_time asc;