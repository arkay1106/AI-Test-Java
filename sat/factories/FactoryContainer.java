package nvr.sat.factories;

public class FactoryContainer {
	private CuraPageObjectsFactory curaPageObjectsFactory;
	private WebDriverFactory webDriverFactory;
	public FactoryContainer() {
		webDriverFactory = new WebDriverFactory();
		curaPageObjectsFactory = new CuraPageObjectsFactory(webDriverFactory.getWebDriver());
	}
	public WebDriverFactory getWebDriverFactory() {
		return webDriverFactory;
	}

	public CuraPageObjectsFactory getCuraPageObjectsFactory() {
		return curaPageObjectsFactory;
	}

}
