import { defineConfig } from 'vitest/config';

export default defineConfig({
    test: {
        projects: [
            {
                // Note: Since we are just doing a demo, we do "test alongside code-to-be-tested".
                //       https://nuxt.com/docs/4.x/getting-started/testing#setup recommends separate test directories
                // TODO add components tests for Vue and e2e with playwright
                test: {
                    name: 'unit',
                    include: ['**/*.{test,spec}.ts'],
                    environment: 'node'
                }
            },
        ],
    },
});
