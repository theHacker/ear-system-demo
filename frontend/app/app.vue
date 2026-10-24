<script setup>
useHead({
    meta: [
        { name: 'viewport', content: 'width=device-width, initial-scale=1' }
    ],
    link: [
        { rel: 'icon', href: '/favicon.ico' }
    ],
    htmlAttrs: {
        lang: 'en'
    },
});

const title = 'ear system – demo application';
const description = 'A demo application implementing reporting quantities';

useSeoMeta({
    title,
    description,
});
</script>

<template>
    <UApp>
        <UHeader :title="title">
            <template #right>
                <UColorModeButton />
            </template>
        </UHeader>

        <UMain>
            <NuxtPage />
        </UMain>

        <USeparator icon="i-simple-icons-nuxtdotjs" />

        <UFooter>
            <template #left>
                <p class="text-sm text-muted">
                    Built with Nuxt UI • © {{ new Date().getFullYear() }}
                </p>
            </template>
        </UFooter>
    </UApp>
</template>
