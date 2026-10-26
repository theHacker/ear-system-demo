// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: [
        '@nuxt/eslint',
        '@nuxt/test-utils/module',
        '@nuxt/ui'
    ],

    devtools: {
        enabled: true
    },

    css: ['~/assets/css/main.css'],

    routeRules: {
        '/': { prerender: true },
        '/api/**': { proxy: 'http://localhost:8080/**' },
    },

    compatibilityDate: '2026-06-30',

    eslint: {
        config: {
            stylistic: {
                commaDangle: 'never',
                braceStyle: '1tbs',
                indent: 4,
                quotes: 'single',
                semi: true
            }
        }
    },
});
