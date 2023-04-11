// 查询列表接口
const getBookPage = (params) => {
  return $axios({
    url: '/book/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteBook = (ids) => {
  return $axios({
    url: '/book',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
const editBook = (params) => {
  return $axios({
    url: '/book',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addBook = (params) => {
  return $axios({
    url: '/book',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryBookById = (id) => {
  return $axios({
    url: `/book/${id}`,
    method: 'get'
  })
}

// 获取书籍分类列表
const getCategoryList = (params) => {
  return $axios({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 查书籍列表的接口
const queryBookList = (params) => {
  return $axios({
    url: '/book/list',
    method: 'get',
    params
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const bookStatusByStatus = (params) => {
  return $axios({
    url: `/book/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}