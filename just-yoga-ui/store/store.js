import articles from "@/data/articles.json";

export default {
  state: () => ({
    articles,
    drawer: false,
    items: [
      {
        text: "Home",
        to: "/",
      },
      {
        text: "About",
        href: "#about",
      },
      {
        text: "Profile",
        to: "/profile",
      },
      {
        text: "Directory",
        to: "/directory",
      },
    ],
  }),
  getters: {
    categories: (state) => {
      const categories = [];

      for (const article of state.articles) {
        if (
          !article.category ||
          categories.find((category) => category.text === article.category)
        ) {
          continue;
        }

        const text = article.category;

        categories.push({
          text,
          to: `/category/${text}`,
        });
      }
      return categories.sort().slice(0, 4);
    },
    links: (state, getters) => {
      return state.items;
    },
    drawer: (state) => {
      return state.drawer;
    },
  },
  mutations: {
    setDrawer: (state, payload) => (state.drawer = payload),
    toggleDrawer: (state) => (state.drawer = !state.drawer),
  },
  actions: {},
};
