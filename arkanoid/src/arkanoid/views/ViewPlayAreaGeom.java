package arkanoid.views;

import arkanoid.BoundingBox;
import arkanoid.BaseColor;
import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.Settings;

import arkanoid.models.ModelPlayArea;
import arkanoid.models.entities.Ball;
import arkanoid.models.entities.Bonus.Bonus;
import arkanoid.models.entities.Bricks.Brick;
import arkanoid.models.entities.Club;
import arkanoid.models.entities.Wall;
import java.util.List;



import java.util.Observable;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/** Classe que define a Vista da Àrea de Jogo.
 *
 * @author sPeC
 */
public class ViewPlayAreaGeom extends ArkanoidView {

    ModelPlayArea mPlayArea;
    UnicodeFont mGenericFont;
    UnicodeFont mGameOverFont;

    /** Constructor da classe.
     * 
     * @param _playArea Modelo de Àrea de Jogo
     * @throws SlickException 
     */
    public ViewPlayAreaGeom(ModelPlayArea _playArea) throws SlickException {
        mPlayArea = _playArea;

        // Cria a fonte para apresentar o texto
        mGenericFont = new UnicodeFont("fonts\\Comiccity.ttf", 24, false, false);
        mGenericFont.addAsciiGlyphs();
        mGenericFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        mGenericFont.loadGlyphs();

        // Fonte para o gameover
        mGameOverFont = new UnicodeFont("fonts\\JerseyLetters.ttf", 100, false, false);
        mGameOverFont.addGlyphs("GAME OVER!");
        mGameOverFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        mGameOverFont.loadGlyphs();
    }


    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /** Renderiza entidades no ecrã.
     */
    @Override
    public void render() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        // Nesta vista não usamos texturas para desenhar os elementos
        // por isso temos que as desactivar, ou os elementos não são desenhados
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        drawWalls();
        drawBricks();
        drawClub();
        drawBall();
        drawBonus();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        if (GameState.currentState() == GameStateType.GAME_OVER) {
            DrawGameOver();
            mPlayArea.getBonus().clear();
        }



        drawHeaderText();
    }

    /** Desenha ecrã de Game Over.
     */
    private void DrawGameOver() {
        float x = Settings.PLAY_AREA_START_X;
        float y = Settings.PLAY_AREA_START_Y;

        float width = Settings.DISPLAY_WIDTH - Settings.PLAY_AREA_START_X * 2;
        float height = Settings.DISPLAY_HEIGHT - Settings.PLAY_AREA_START_Y;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        drawQuad(new BoundingBox(x, y, width, height), new BaseColor(0.0f, 0.0f, 0.0f, 0.90f));
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        x += (width / 2) - mGameOverFont.getWidth("GAME OVER!") / 2;
        y += (height / 2) - mGameOverFont.getHeight("GAME OVER!");

        mGameOverFont.drawString(x, y, "GAME OVER!", Color.red);
    }

    /** Desenha texto no cabeçalho (pontos, nivel, vidas).
     */
    private void drawHeaderText() {
        float y = (Settings.PLAY_AREA_START_Y / 2) - (mGenericFont.getHeight("P") / 2);
        float x = Settings.PLAY_AREA_START_X;

        // Pontos
        mGenericFont.drawString(x, y, "Pontos: " + mPlayArea.getPlayer().getScore(), Color.black);

        // Nivel
        String tmpString = "Nivel: " + mPlayArea.getLoadedLevel().getLevelNumber()
                + " - '" + mPlayArea.getLoadedLevel().getName() + "'";
        x = (Settings.DISPLAY_WIDTH / 2) - (mGenericFont.getWidth(tmpString) / 2);
        mGenericFont.drawString(x, y, tmpString, Color.black);

        // Vidas
        tmpString = "Vidas: " + mPlayArea.getPlayer().getLifes();
        x = (Settings.DISPLAY_WIDTH - Settings.PLAY_AREA_START_X) - mGenericFont.getWidth(tmpString);
        mGenericFont.drawString(x, y, tmpString, Color.black);
    }

    /** Desenha paredes no ecrã.
     */
    private void drawWalls() {
        Wall[] tmpWalls = mPlayArea.getWalls();

        for (int i = 0; i < 3; i++) {
            drawQuad(tmpWalls[i].getBoundingBox(), tmpWalls[i].getColor());
        }
    }

    /** Desenha Tijolos no ecrã.
     */
    private void drawBricks() {

        for (int l = 0; l < Settings.MAX_BRICK_ROWS; l++) {
            for (int c = 0; c < Settings.MAX_BRICK_COLUMNS; c++) {

                Brick tmpBrick = mPlayArea.getLoadedLevel().getBrick(l, c);
                if (tmpBrick == null || !tmpBrick.isActive()) {
                    continue;
                }
                drawQuad(tmpBrick.getBoundingBox(), tmpBrick.getColor());
            }
        }
    }
    
    /** Desenha Raquete no ecrã.
     */
    private void drawClub() {

        Club tmpClub = mPlayArea.getClub();

        drawQuad(tmpClub.getBoundingBox(), tmpClub.getColor());
    }

    /** Desenha Bola no ecrã.
     */
    private void drawBall() {
        Ball tmpBall = mPlayArea.getBall();

        drawQuad(tmpBall.getBoundingBox(), tmpBall.getColor());
    }

    /**Desenha Poligono no ecrã.
     * 
     * @param _quad BoundingBox do elemento a desenhar
     * @param _color Cor do poligono
     */
    private void drawQuad(BoundingBox _quad, BaseColor _color) {


        // set the color of the quad (R,G,B,A)
        GL11.glColor4f(_color.getR(), _color.getG(), _color.getB(), _color.getA());

        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(_quad.getX(), _quad.getY());
        GL11.glVertex2f(_quad.getX() + _quad.getWidth(), _quad.getY());
        GL11.glVertex2f(_quad.getX() + _quad.getWidth(), _quad.getY() + _quad.getHeight());
        GL11.glVertex2f(_quad.getX(), _quad.getY() + _quad.getHeight());
        GL11.glEnd();
    }
    
    private void drawBonus() {
        List<Bonus> tmpBonus = mPlayArea.getBonus();
        
        for(Bonus bonus: tmpBonus){
            drawQuad(bonus.getBoundingBox(),bonus.getColor() );
        }
        
    }
}
