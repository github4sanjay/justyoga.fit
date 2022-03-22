export default {
  getMeta(
    title,
    description,
    url,
    image,
    keywords,
    video,
    type,
    linkPrev,
    linkNext
  ) {
    if (description && description.length > 150) {
      description = description.substring(0, 150);
    }
    let meta = {
      title: title,
      link: [{ rel: "canonical", href: url }],
      meta: {
        description: {
          name: "description",
          content: description
        },
        keywords: {
          name: "keywords",
          content: keywords
        },
        equiv: {
          "http-equiv": "Content-Type",
          content: "text/html; charset=UTF-8"
        },
        ogSiteName: {
          property: "og:site_name",
          content: "JustYoga"
        },
        ogType: {
          property: "og:type",
          content: type
        },
        ogUrl: {
          property: "og:url",
          content: url
        },
        ogTitle: {
          property: "og:title",
          content: title
        },
        ogDesc: {
          property: "og:description",
          content: description
        },
        ogImage: {
          property: "og:image",
          itemprop: "image",
          content: image
        },
        ogImageWidth: {
          property: "og:image:width",
          content: "300"
        },
        ogImageHeight: {
          property: "og:image:height",
          content: "300"
        },
        twitterCard: {
          property: "twitter:card",
          content: "summary_large_image"
        },
        twitterUrl: {
          property: "twitter:url",
          content: url
        },
        twitterTitle: {
          property: "twitter:title",
          content: title
        },
        twitterDesc: {
          property: "twitter:description",
          content: description
        },
        twitterImage: {
          property: "twitter:image",
          content: image
        },
        twitterSite: {
          property: "twitter:site",
          content: "justyoga.fit"
        }
      }
    };

    if (video) {
      meta.meta["ogVideo"] = {
        property: "og:video",
        itemprop: "video",
        content: video
      };
      meta.meta["ogVideoWidth"] = {
        property: "og:video:width",
        content: "300"
      };
      meta.meta["ogVideoHeight"] = {
        property: "og:video:height",
        content: "300"
      };
      meta.meta["ogTwitterVideo"] = {
        property: "twitter:player",
        content: video
      };
      meta.meta["ogTwitterVideoWidth"] = {
        property: "twitter:player:width",
        content: "300"
      };
      meta.meta["ogTwitterVideoHeight"] = {
        property: "twitter:player:height",
        content: "300"
      };
    }

    if (linkNext) {
      meta.link.push({ rel: "next", href: linkNext });
    }
    if (linkPrev) {
      meta.link.push({ rel: "prev", href: linkPrev });
    }
    return meta;
  }
};
