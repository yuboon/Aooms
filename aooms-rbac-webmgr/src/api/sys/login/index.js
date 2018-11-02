import request from '@/plugin/axios'

export function AccountLogin (data) {
  return request({
    url: '/aooms/rbac/login',
    method: 'post',
    data
  })
}
