const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/Index.vue") },
      { path: "/error404", component: () => import("pages/Error404.vue") },
      { path: "/error500", component: () => import("pages/Error500.vue") },
      { path: "/login", component: () => import("pages/login.vue") },
      {
        path: "/collections/:page",
        name: "collections",
        component: () => import("pages/collections.vue")
      },
      {
        path: "/collection-detail/:id",
        name: "collections-detail",
        component: () => import("pages/collection/index.vue")
      },
      {
        path: "/users/:id",
        name: "users",
        component: () => import("pages/user/users.vue"),
        children: [
          { path: "detail", component: () => import("pages/user/detail.vue") },
          {
            path: "expertise",
            component: () => import("pages/user/expertise.vue")
          },
          {
            path: "images/:page",
            component: () => import("pages/user/images.vue")
          },
          {
            path: "videos/:page",
            component: () => import("pages/user/videos.vue")
          },
          {
            path: "reviews/:page",
            component: () => import("pages/user/reviews.vue")
          },
          {
            path: "blogs/:page",
            component: () => import("pages/user/blogs.vue")
          }
        ]
      },
      {
        path: "/reviews/:id",
        name: "reviews",
        component: () => import("pages/reviews.vue")
      },
      {
        path: "/blogs/:id",
        name: "blogs",
        component: () => import("pages/blogs.vue")
      },
      {
        path: "/images/:id",
        name: "images",
        component: () => import("pages/images.vue")
      },
      {
        path: "/videos/:id",
        name: "videos",
        component: () => import("pages/videos.vue")
      },
      {
        path: "/directory",
        name: "directory",
        component: () => import("pages/directory/index.vue"),
        children: [
          {
            path: "trainers/:page",
            component: () => import("pages/directory/index/trainers.vue")
          },
          {
            path: "images/:page",
            component: () => import("pages/directory/index/images.vue")
          },
          {
            path: "videos/:page",
            component: () => import("pages/directory/index/videos.vue")
          },
          {
            path: "blogs/:page",
            component: () => import("pages/directory/index/blogs.vue")
          }
        ]
      },
      {
        path: "/directory/:country",
        name: "countryDirectory",
        component: () => import("pages/directory/country/index.vue"),
        children: [
          {
            path: "trainers/:page",
            component: () =>
              import("pages/directory/country/index/trainers.vue")
          },
          {
            path: "images/:page",
            component: () => import("pages/directory/country/index/images.vue")
          },
          {
            path: "videos/:page",
            component: () => import("pages/directory/country/index/videos.vue")
          },
          {
            path: "blogs/:page",
            component: () => import("pages/directory/country/index/blogs.vue")
          }
        ]
      },
      {
        path: "/directory/:country/:state",
        name: "stateDirectory",
        component: () => import("pages/directory/country/state/index.vue"),
        children: [
          {
            path: "trainers/:page",
            component: () =>
              import("pages/directory/country/state/index/trainers.vue")
          },
          {
            path: "images/:page",
            component: () =>
              import("pages/directory/country/state/index/images.vue")
          },
          {
            path: "videos/:page",
            component: () =>
              import("pages/directory/country/state/index/videos.vue")
          },
          {
            path: "blogs/:page",
            component: () =>
              import("pages/directory/country/state/index/blogs.vue")
          }
        ]
      },
      {
        path: "/directory/:country/:state/:locality",
        name: "localityDirectory",
        component: () =>
          import("pages/directory/country/state/locality/index.vue"),
        children: [
          {
            path: "trainers/:page",
            component: () =>
              import(
                "pages/directory/country/state/locality/index/trainers.vue"
              )
          },
          {
            path: "images/:page",
            component: () =>
              import("pages/directory/country/state/locality/index/images.vue")
          },
          {
            path: "videos/:page",
            component: () =>
              import("pages/directory/country/state/locality/index/videos.vue")
          },
          {
            path: "blogs/:page",
            component: () =>
              import("pages/directory/country/state/locality/index/blogs.vue")
          }
        ]
      },
      {
        path: "/directory/:country/:state/:locality/:sub",
        name: "subDirectory",
        component: () =>
          import("pages/directory/country/state/locality/sub/index.vue"),
        children: [
          {
            path: "trainers/:page",
            component: () =>
              import(
                "pages/directory/country/state/locality/sub/index/trainers.vue"
              )
          },
          {
            path: "images/:page",
            component: () =>
              import(
                "pages/directory/country/state/locality/sub/index/images.vue"
              )
          },
          {
            path: "videos/:page",
            component: () =>
              import(
                "pages/directory/country/state/locality/sub/index/videos.vue"
              )
          },
          {
            path: "blogs/:page",
            component: () =>
              import(
                "pages/directory/country/state/locality/sub/index/blogs.vue"
              )
          }
        ]
      },
      {
        path: "/admin/",
        name: "admin",
        component: () => import("pages/admin/index.vue")
      },
      {
        path: "/tech/",
        name: "tech",
        component: () => import("pages/tech.vue")
      }
    ]
  }
];

// Always leave this as last one
// if (process.env.MODE !== 'ssr') {
//   routes.push({
//     path: '*',
//     component: () => import('pages/Error404.vue')
//   })
// }

export default routes;
