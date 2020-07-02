-- LUA脚本可以传递参数，参数有两个固定属性 KEYS／ARGV
-- NOTE：LUA脚本可以保证原子性
-- KEYS[1] 资源（手机号...）
-- ARGV[1] 超时时间
-- ARGV[2] 次数
local times = redis.call('incr', KEYS[1])
if times == 1 then
    redis.call('expire', KEYS[1], ARGV[1])
end

if times > tonumber(ARGV[2])  then
    return 0 -- 失败
end

return 1 -- 成功
