<template>
    <UApp>
        <UHeader title="ear system">
            <template #default>
                <UNavigationMenu
                    :items="navigationMenuItems"
                />
            </template>

            <template #body>
                <UNavigationMenu
                    :items="navigationMenuItems"
                    orientation="vertical"
                />
            </template>

            <template #right>
                <UColorModeButton />
            </template>
        </UHeader>

        <UMain>
            <NuxtPage />
        </UMain>

        <USeparator />

        <UFooter>
            <p class="text-sm text-muted">
                Built with Nuxt UI
            </p>
        </UFooter>
    </UApp>
</template>

<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui';

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

const navigationMenuItems = ref<NavigationMenuItem[]>([
    {
        label: 'Home',
        to: '/',
    },
    {
        label: 'Quantity reports',
        description: 'Show all quantity reports.',
        to: '/quantity-reports/overview',
    },
    {
        label: 'Add quantity report',
        description: 'Adds a new quantity report.',
        to: '/quantity-reports/add',
    },
]);

useSeoMeta({
    title,
    description,
});
</script>
