package vvsvintsitsky.testing.webapp.page.home;

import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.product.ProductPage;

public class HomePage extends AbstractPage {

    public HomePage() {
        super();
        add(new Link("linkproduct") {
            @Override
            public void onClick() {
                setResponsePage(new ProductPage());
            }
        });
    }

}
