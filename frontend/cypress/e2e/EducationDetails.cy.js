describe('education details', () => {
    beforeEach(() => {
        cy.visit('/education/1')
    })

    it('shows details', () => {
        cy.get('.card-header a')
            .should('have.text', 'Institution A')
            .should('have.attr', 'href')
        cy.get('.card-title').should('have.text', 'Bildungsangebot 1')
        cy.get('.card-text p').should('have.text', 'Beschreibung Bildungsangebot 1')
    })

    it('can matriculate', () => {
        cy.get('.card-text button')
            .should('have.text', 'Einschreiben')
            .should('be.enabled')
            .click()

        cy.get('.card-text button')
            .should('be.disabled')
    })
})
